package org.example;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class LabThree {
    private static final String mockUrl = "https://b2a7ff34-7688-4d9f-9ae1-bfe5f9ac1faa.mock.pstmn.io";
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = mockUrl;}
    @Test
    public void testGetSuccess() {
        given().get("/ownerName/success")
                .then().statusCode(HttpStatus.SC_OK)
                .and().body("name", equalTo("Анастасія Тимофєєва"));}
    @Test
    public void testGetUnsuccess() {
        given().get("/ownerName/unsuccess")
                .then().statusCode(HttpStatus.SC_FORBIDDEN);}
    @Test
    public void testPostSuccess() {
        given().header("Content-Type", "application/json") // Кажемо, що це JSON
                .body("{}") // Даємо йому порожнє тіло, щоб не лаявся на формат
                .queryParam("permission", "yes")
                .post("/createSomething")
                .then().statusCode(HttpStatus.SC_OK);}
    @Test
    public void testPostUnsuccess() {
        given().post("/createSomething")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);}
    @Test
    public void testPutError() {
        given().put("/updateMe")
                .then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);}
    @Test
    public void testDelete() {
        given().header("SessionID", "123456789").delete("/deleteWorld")
                .then().statusCode(HttpStatus.SC_GONE);}
}