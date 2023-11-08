package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ApiTesting {
    String nameToAdd = "Biology";
    String url = "http://localhost:8080/categories";

    int id = 1;

    @Test
    @DisplayName("Gets the full list of categories")
    void getCategoriesName() {

        given().
                when().
                get(url).
                then().
                statusCode(200).log().all();
    }

    @Test
    @DisplayName("Add new category name")
    void addCategoryName() {

        Map<String, String> book = new HashMap<>();
        book.put("name", nameToAdd);


        given().

                contentType(ContentType.JSON).
                body(book).

                when().
                post(url).
                then().
                statusCode(201);


    }

    @Test
    @DisplayName("Checks if the category name can be found in the list")
    public void findCategoryNameInTheList() {
        String categoryName = "Biology";

        Response response = given()
                .baseUri("http://localhost:8080")
                .when()
                .get("/categories");
        response.then()
                .statusCode(200);
        response.then()
                .body("name", hasItem(categoryName));
    }

    @Test
    @DisplayName("Checks whether it is possible to add a category name that does not meet the validation")
    void addWrongCategoryName() {
        Map<String, String> categories = new HashMap<>();
        categories.put("name", "geography");

        given().
                contentType(ContentType.JSON).
                body(categories).
                when().
                post(url).
                then().
                assertThat().
                contentType(ContentType.TEXT).
                statusCode(400).
                body(equalTo("Category name must start with an uppercase letter, followed by lowercase letters, without numbers, consecutive repeated characters, and a length between 5 and 50 characters"));


    }

    @Test
    void deleteByCategoryName() {

        Map<String, String> book = new HashMap<>();
        book.put("name", nameToAdd);


        given().

                contentType(ContentType.JSON).
                body(book).

                when().
                delete(url).
                then().
                statusCode(405);

    }

    @Test
    @DisplayName("Checks whether the category name can be found by id")
    void findByCategoryId() {
        int expectedId = 1;
        given().pathParams("id", id).
                when().
                get(url + "/{id}").
                then().
                statusCode(200).log().body().
                assertThat().
                body("id", equalTo(expectedId));

    }

    @Test
    @DisplayName("Checks whether the system allows deleting the category name according to id")
    void deleteByCategoryId() {

        given().pathParams("id", id).
                when().
                delete(url + "/{id}").
                then().
                statusCode(405);

    }

    @Test
    @DisplayName("Checks whether the system does not allow adding a name that is empty")
    public void addEmptyCategory() {

        String validationNullEmpty = "{ \"name\": \"\" }";


        Response response = given()
                .contentType(ContentType.JSON)
                .body(validationNullEmpty)
                .when()
                .post(url);


        response.then().statusCode(400)
                .contentType(ContentType.TEXT);


        response.then().body(containsString("Category name must start with an uppercase letter, followed by lowercase letters, without numbers, consecutive repeated characters, and a length between 5 and 50 characters")).
                body(containsString("The field must not be empty"));
    }

    @Test
    @DisplayName("Checks whether the system does not allow adding a name that is null")
    public void addNullCategory() {

        String validationNullEmpty = "{}";


        Response response = given()
                .contentType(ContentType.JSON)
                .body(validationNullEmpty)
                .when()
                .post(url);


        response.then().statusCode(400)
                .contentType(ContentType.TEXT);


        response.then().body(containsString("The field must not be empty"))
                .body(containsString("The field must not be null"));
    }

    @Test
    @DisplayName("Checks whether the system does not allow adding a name that does not meet the validation")
    public void validationFormTest() {

        String requestBody = "{ \"name\": \"invalid-category-name123\" }";


        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(url);

        response.then().statusCode(400);


        response.then().body(containsString("Category name must start with an uppercase letter, followed by lowercase letters, without numbers, consecutive repeated characters, and a length between 5 and 50 characters"));
    }

    @Test
    @DisplayName("Adds a category that has already been added before")
    void addExistedCategory() {
        Map<String, String> categories = new HashMap<>();
        categories.put("name", nameToAdd);

        given().
                contentType(ContentType.JSON).
                body(categories).
                when().
                post(url).
                then().
                assertThat().
                contentType(ContentType.TEXT).
                statusCode(400).
                body(equalTo("Category already exists"));


    }

    @ParameterizedTest
    @DisplayName("Adds categories that should pass by validation")
    @CsvFileSource(files = "src/main/resources/Categories.csv")
    void addManyCategories(String categoryName) {

        Map<String, String> book = new HashMap<>();
        book.put("name", categoryName);


        given().

                contentType(ContentType.JSON).
                body(book).

                when().
                post(url).
                then().
                statusCode(201);


    }

    @Test
    @DisplayName("Checks that the array is not empty")
    void checkListMassive() {
        Response response = given()
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response();


        response.then().body("$", everyItem(hasKey("id"))).body("$", everyItem(hasKey("name")));

    }

    @Test
    @DisplayName("FindByNotExistingCategoryId")
    void findByNotExistingCategoryId() {

        given().pathParams("id", 666).
                when().
                get(url + "/{id}").
                then().
                statusCode(404);
    }

    @Test
    @DisplayName("RequestWithIncorrectHttpMethod")
    void requestWithIncorrectHttpMethod() {

        given().
                when().
                put(url).
                then().
                statusCode(405);
    }

    @Test
    @DisplayName("Update Category Name")
    void updateCategoryName() {

        String updatedName = "Rock";
        int id = 1;

        Map<String, String> updatedBook = new HashMap<>();
        updatedBook.put("name", updatedName);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(updatedBook)
                .when()
                .put(url + "/{id}")
                .then()
                .statusCode(200);


        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(updatedBook)
                .when()
                .get(url + "/{id}")
                .then()
                .contentType(ContentType.JSON)
                .body("name", equalTo(updatedName));

    }
}














