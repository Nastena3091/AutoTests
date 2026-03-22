package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetTest {
    private static final String baseUrl = "https://petstore.swagger.io/v2";
    private static final String PET = "/pet", PET_ID = PET + "/{petId}";

    private final int listNum = 19;
    private final int groupNum = 122231;
    private final String studName = "Анастасія Тимофєєва";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test(priority = 1)
    public void verifyCreatePet() {
        Map<String, Object> category = Map.of("id", groupNum, "name", "Кіт");
        Map<String, Object> body = Map.of("id", listNum, "category", category, "name", studName, "status", "available");
        given().body(body).post(PET).then().statusCode(HttpStatus.SC_OK);
    }

    @Test(priority = 2)
    public void verifyGetPet() {
        given().pathParam("petId", listNum).get(PET_ID)
                .then().statusCode(HttpStatus.SC_OK).and().body("name", equalTo(studName));
    }

    @Test(priority = 3)
    public void verifyUpdatePet() {
        Map<String, Object> category = Map.of("id", groupNum, "name", "Кіт");
        Map<String, Object> body = Map.of("id", listNum, "category", category, "name", studName, "status", "sold");
        given().body(body).put(PET).then().statusCode(HttpStatus.SC_OK).and().body("status", equalTo("sold"));
    }
}