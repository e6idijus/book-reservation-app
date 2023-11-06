package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
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
    void getCategoriesName() {

        given().
                when().
                get(url).
                then().
                statusCode(200).log().all();
    }

    @Test
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
    void findCategoryName() {
        String expectedName = "Biology";

        given().pathParams("id", id).
                when().
                get(url + "/{id}").
                then().
                assertThat().
                body("name", equalTo(expectedName));


    }

    @Test
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
    void findByCategoryId() {
        int expectedId = 1;
        given().pathParams("id", id).
                when().
                get(url + "/{id}").
                then().
                statusCode(200).
                assertThat().
                body("id", equalTo(expectedId));

    }

    @Test
    void deleteByCategoryId() {

        given().pathParams("id", id).
                when().
                delete(url + "/{id}").
                then().
                statusCode(405);

    }

    @Test
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
    @CsvFileSource(files = "src/main/resources/Categories.csv")
    void parameterizedTest(String categoryName) {

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
}









