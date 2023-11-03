package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTesting {
    String nameToAdd = "Samanele";

    int id = 1;

    @Test
    void getCategoriesName() {

        given().
                when().
                get("http://localhost:8080/categories").
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
                post("http://localhost:8080/categories").
                then().
                statusCode(201);



    }

    @Test
    void findCategoryName() {
        String expectedName = "Biology";

        given().pathParams("id", id).
                when().
                get("http://localhost:8080/categories/{id}").
                then().
                assertThat().
                body("name", equalTo(expectedName)).
                log().body();

    }

    @Test
    void addWrongCategoryName() {
        Map<String, String> categories = new HashMap<>();
        categories.put("name", "geography");

        given().
                contentType(ContentType.JSON).
                body(categories).
                when().
                post("http://localhost:8080/categories").
                then().
                assertThat().
                contentType(ContentType.TEXT).
                statusCode(400).
                body(equalTo("Category name must start with an uppercase letter, followed by lowercase letters, without numbers, consecutive repeated characters, and a length between 5 and 50 characters")).
                log().body();

    }

    @Test
    void deleteByCategoryName() {

        Map<String, String> book = new HashMap<>();
        book.put("name", nameToAdd);


        given().

                contentType(ContentType.JSON).
                body(book).

                when().
                delete("http://localhost:8080/categories").
                then().
                statusCode(405);

    }

    @Test
    void findByCategoryId() {
        int expectedId = 1;
        given().pathParams("id", id).
                when().
                get("http://localhost:8080/categories/{id}").
                then().
                statusCode(200).
                assertThat().
                body("id", equalTo(expectedId)).log().body();

    }

    @Test
    void deleteByCategoryId() {

        given().pathParams("id", id).
                when().
                delete("http://localhost:8080/categories/{id}").
                then().
                statusCode(405).
                log().body();
    }

    @Test
    public void validationTest() {

        String validationNullEmpty = "{ \"name\": null }";


        Response response = given()
                .contentType(ContentType.JSON)
                .body(validationNullEmpty)
                .when()
                .post("http://localhost:8080/categories");


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
                .post("http://localhost:8080/categories");

        response.then().statusCode(400);


        response.then().body(containsString("Category name must start with an uppercase letter, followed by lowercase letters, without numbers, consecutive repeated characters, and a length between 5 and 50 characters"));
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
                post("http://localhost:8080/categories").
                then().
                statusCode(201);


    }

}





