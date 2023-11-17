package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BooksApiTesting {

    @Test
    @Order(1)
    @DisplayName("Looking for books when the list of books is empty")
    void emptyList() {

        RestAssured.baseURI = "http://localhost:8080";

        Response response = given()
                .when()
                .get("/books");
        response.then().statusCode(404)
                .contentType(ContentType.TEXT);

        response.then().body(containsString("No books found"));


    }

    @Test
    @Order(2)
    @DisplayName("Adds the book and checks whether it has been added correctly")
    void addBook() {

        int expectedId = 1;
        String title = "Loki";
        String author = "Mackenzi Lee";
        int expectedPages = 250;
        String expectedLanguage = "English";
        String expectedISBN = "1-9028-9465-1";


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
    @Order(3)
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
    @Order(3)
    @DisplayName("Add book with not existed category")
    void addBookWithNotExistedCategory() {
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
    @Order(4)
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

    @Test
    @Order(5)
    @DisplayName("Checks whether the book name can be found by id")
    void findBookById() {
        int expectedId = 1;
        given().pathParams("id", expectedId).
                when().
                get("/books/{id}").
                then().
                statusCode(200).log().body().
                assertThat().
                body("id", Matchers.equalTo(expectedId));

    }

    @Test
    @Order(6)
    @DisplayName("Find By Not Existing Book Id")
    void findByNotExistingBookId() {
        int expectedId = 555;
        given().pathParams("id", expectedId).
                when().
                get("/books/{id}").
                then().
                statusCode(404);
    }

    @Test
    @Order(7)
    @DisplayName("Checks if the book title can be found in the list")
    public void findBookTitleInTheList() {
        String bookTitle = "Loki";

        Response response = given()
                .baseUri("http://localhost:8080")
                .when()
                .get("/books");
        response.then()
                .statusCode(200);
        response.then()
                .body("title", hasItem(bookTitle));
    }

    @Test
    @Order(8)
    @DisplayName("Checks that the array is not empty")
    void checkListMassive() {
        Response response = given()
                .when()
                .get("/books")
                .then()
                .statusCode(200)
                .extract()
                .response();


        response.then()
                .body("$", everyItem(hasKey("title")))
                .body("$", everyItem(hasKey("id")))
                .body("$", everyItem(hasKey("author")))
                .body("$", everyItem(hasKey("categories")))
                .body("$", everyItem(hasKey("description")))
                .body("$", everyItem(hasKey("pictureUrl")))
                .body("$", everyItem(hasKey("pages")))
                .body("$", everyItem(hasKey("isbn")))
                .body("$", everyItem(hasKey("publicationDate")))
                .body("$", everyItem(hasKey("language")));

    }

    @Test
    @Order(9)
    @DisplayName("Request With Incorrect Http Method")
    void requestWithIncorrectHttpMethod() {

        given().
                when().
                put("/books").
                then().
                statusCode(405);
    }

    @Test
    @Order(10)
    @DisplayName("Add Book with wrong picture Url format")
    void addBookWithWrongPictureUrlFormat() {
        Map<String, Object> book = new HashMap<>();
        book.put("title", "Hannibal");
        book.put("author", "Thomas Harris");

        List<Map<String, String>> categories = List.of(
                Map.of("name", "Fiction"),
                Map.of("name", "Literary")
        );
        book.put("categories", categories);

        book.put("description", "Hannibal is a psychological horror novel by American author Thomas Harris, published in 1999.");
        book.put("pictureUrl", "https://www.booklender.com/jackets/7/0/7/9780804172707.svg");
        book.put("pages", 200);
        book.put("publicationDate", "2001-09-15");
        book.put("language", "English");
        book.put("isbn", "1-9028-9465-7");


        Response response = given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post("/books");
        response.then().statusCode(400)
                .contentType(ContentType.TEXT);
        response.then().body(containsString("URl should start with either \"http://\" or \"https://\" and end with \".jpg\" or \".png"));

    }
}

