package org.example;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTesting {
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
        book.put("name", "Sparkis");


        given().

                contentType(ContentType.JSON).
                body(book).

                when().
                post("http://localhost:8080/categories").
                then().
                log().body();

    }

    @Test
    void findCategoryName() {

        given().


                when().
                get("http://localhost:8080/categories").
                then().
                log().body().
                assertThat().
                body("name[0]", equalTo("Sparkis"));

    }

    @Test
    void addWrongCategoryName() {

        Map<String, String> book = new HashMap<>();
        book.put("name", "Sparkis ir Draugai");


        given().

                contentType(ContentType.JSON).
                body(book).

                when().
                post("http://localhost:8080/categories").
                then().
                statusCode(500).
                log().body();

    }

    @Test
    void deleteByCategoryName() {

        Map<String, String> book = new HashMap<>();
        book.put("name", "Sparkis");


        given().

                contentType(ContentType.JSON).
                body(book).

                when().
                delete("http://localhost:8080/categories").
                then().
                statusCode(405).
                log().body();
    }

    @Test
    void findByCategoryId() {
        int id = 3;
        given().pathParams("id", id).
                when().
                get("http://localhost:8080/categories/{id}").
                then().
                statusCode(200).
                log().body();
    }
    @Test
    void deleteByCategoryId() {
        int id = 3;
        given().pathParams("id", id).
                when().
                delete("http://localhost:8080/categories/{id}").
                then().
                statusCode(405).
                log().body();
    }
}