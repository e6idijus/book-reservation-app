package lt.techin.bookreservationapp;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@SpringBootTest
class BookReservationAppApplicationTests {
	private  final static String URL = "http://localhost:8080/";

	@Test
	void contextLoads() {
	}
	void checkIdTest() {
		List<UserData> users = given()
		.when()
		.contentType(ContentType.JSON)
		.get(URL+"categories")
		.then().log().all()
		.extract().body().jsonPath().getList("data", UserData.class);
	}

}
