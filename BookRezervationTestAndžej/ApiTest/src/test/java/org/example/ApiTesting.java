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
    void addCategory() {

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
            void findCategory(){

        given().


                when().
                get("http://localhost:8080/categories").
                then().
                log().body().
                assertThat().
                body("name[0]", equalTo("Julius"));

    }
    @Test
    void addWrongCategory() {

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
}