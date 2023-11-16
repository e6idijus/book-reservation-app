package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsString;

public class BooksApiTesting {

    @Test
    @DisplayName("Adds the book and checks whether it has been added correctly")
    void addBook() {

        int expectedId = 1;
        String title = "Loki";
        String author = "Mackenzi Lee";
        int expectedPages = 250;
        String expectedLanguage = "English";
        String expectedISBN = "1-9028-9465-1";

        RestAssured.baseURI = "http://localhost:8080";


        Map<String, Object> book = new HashMap<>();
        book.put("title", "Loki");
        book.put("author", "Mackenzi Lee");

        List<Map<String, String>> categories = List.of(
                Map.of("name", "Drama"),
                Map.of("name", "Memoir")
        );
        book.put("categories", categories);

        book.put("description", "This is the first of three young adult novels from New York Times best-selling author Mackenzi Lee that explores the untapped potential and duality of heroism of popular characters in the Marvel Universe.");
        book.put("pictureUrl", "https://m.media-amazon.com/images/I/91OoNH1+MHL._SL1500_.jpg");
        book.put("pages", 250);
        book.put("publicationDate", "2019-09-15");
        book.put("language", "English");
        book.put("isbn", "1-9028-9465-1");


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

    @Test
    @DisplayName("Add a book that does not meet the validation")
    void addBookWithEmptyFields() {
        Map<String, Object> book = new HashMap<>();
        book.put("title", "");
        book.put("author", "");

        List<Map<String, String>> categories = List.of(
                Map.of("name", ""),
                Map.of("name", "")
        );
        book.put("categories", categories);

        book.put("description", "");
        book.put("pictureUrl", "");
        book.put("pages", 0);
        book.put("publicationDate", "");
        book.put("language", "");
        book.put("isbn", "");


        Response response = given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post("/books");
        response.then().statusCode(400)
                .contentType(ContentType.TEXT);

        response.then().body(containsString("Book title must start with an uppercase letter, that can be followed by a mix of alphanumeric characters, spaces, and certain punctuation marks")).
                body(containsString("Author's first and last name must start with an uppercase letter, that can be followed by one or more lowercase letters")).
                body(containsString("The description field must not be empty")).
                body(containsString("Description should start with a capital letter and is limited to a maximum of 300 characters")).
                body(containsString("ISBN is incorrect")).
                body(containsString("The ISBN field must not be empty")).
                body(containsString("The picture url field must not be empty")).
                body(containsString("URl should start with either \"http://\" or \"https://\" and end with \".jpg\" or \".png")).
                body(containsString("Publication date field cannot be null")).
                body(containsString("Language must start with an uppercase letter, that can be followed by one or more lowercase letters")).
                body(containsString("Pages field must have a value greater than 0")).
                body(containsString("The language field must not be empty"));
    }
    @Test
    @DisplayName("Add book with not existed category")
    void addBookWithNotExistedCategory(){
        Map<String, Object> book = new HashMap<>();
        book.put("title", "Rent A Little Life");
        book.put("author", "Hanya Yanagihara");

        List<Map<String, String>> categories = List.of(
                Map.of("name", "Fiction"),
                Map.of("name", "Literary")
        );
        book.put("categories", categories);

        book.put("description", "A stunning novel from one of the most exciting new voices in literature, A Little Life follows four college classmates—broke, adrift, and buoyed only by their friendship and ambition—as they move to New York in search of fame and fortune. ");
        book.put("pictureUrl", "https://www.booklender.com/jackets/7/0/7/9780804172707.jpg");
        book.put("pages", 150);
        book.put("publicationDate", "2016-09-15");
        book.put("language", "English");
        book.put("isbn", "1-9028-9465-2");


        Response response = given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post("/books");
        response.then().statusCode(404)
                .contentType(ContentType.TEXT);
        response.then().body(containsString("No such categories exist!"));

    }
    @Test
    @DisplayName("Add Book with existed title")
    void addBookWithExistedTitle() {
        Map<String, Object> book = new HashMap<>();
        book.put("title", "Loki");
        book.put("author", "Hanya Yanagihara");

        List<Map<String, String>> categories = List.of(
                Map.of("name", "Fiction"),
                Map.of("name", "Literary")
        );
        book.put("categories", categories);

        book.put("description", "A stunning novel from one of the most exciting new voices in literature, A Little Life follows four college classmates—broke, adrift, and buoyed only by their friendship and ambition—as they move to New York in search of fame and fortune. ");
        book.put("pictureUrl", "https://www.booklender.com/jackets/7/0/7/9780804172707.jpg");
        book.put("pages", 150);
        book.put("publicationDate", "2016-09-15");
        book.put("language", "English");
        book.put("isbn", "1-9028-9465-3");


        Response response = given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post("/books");
        response.then().statusCode(400)
                .contentType(ContentType.TEXT);
        response.then().body(containsString("Title already exists"));



    }

}

