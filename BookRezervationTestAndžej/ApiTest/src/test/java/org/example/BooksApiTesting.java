package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class BooksApiTesting {

    @Test
    @DisplayName("Adds the book and checks whether it has been added correctly")
    void addBook() {

        int expectedId = 1;
        String title = "Loky";
        String author = "Pan Tadeusz";
        int expectedPages = 250;
        String expectedLanguage = "English";
        String expectedISBN = "1-9028-9465-7";

        RestAssured.baseURI = "http://localhost:8080";


        Map<String, Object> book = new HashMap<>();
        book.put("title", "Loky");
        book.put("author", "Pan Tadeusz");

        List<Map<String, String>> categories = List.of(
                Map.of("name", "Drama"),
                Map.of("name", "Memoir")
        );
        book.put("categories", categories);

        book.put("description", "Frank Herbert’s classic masterpiece—a triumph of the imagination and one of the bestselling science fiction novels of all time");
        book.put("pictureUrl", "https://m.media-amazon.com/images/I/91OoNH1+MHL._SL1500_.jpg");
        book.put("pages", 250);
        book.put("publicationDate", "2005-08-15");
        book.put("language", "English");
        book.put("isbn", "1-9028-9465-7");


        given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post("/books")
                .then()
                .log().all()
                .statusCode(201);


        given().pathParams("id", expectedId).
                when().
                get("/books/{id}").
                then().
                statusCode(200).log().body()
                .body("title", equalTo(title))
                .body("author", equalTo(author))
                .body("pages", equalTo(expectedPages))
                .body("language", equalTo(expectedLanguage))
                .body("isbn", equalTo(expectedISBN));

    }
}
