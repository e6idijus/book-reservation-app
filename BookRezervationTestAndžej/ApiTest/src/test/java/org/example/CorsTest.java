package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CorsTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }
    @Test
    public void testCORSHeaders() {

        Response response = given()
                .header("Origin", "http://localhost:3000")
                .when()
                .get("/categories");

        response.then()
                .header("Access-Control-Allow-Origin", "http://localhost:3000");

        response.then().statusCode(200);


    }
}
