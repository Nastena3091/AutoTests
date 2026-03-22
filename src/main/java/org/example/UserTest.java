package org.example;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class UserTest {
    private static final String baseUrl = "https://petstore.swagger.io/v2";
    private static final String USER = "/user", USER_USERNAME = USER + "/{username}", USER_LOGIN = USER + "/login", USER_LOGOUT = USER + "/logout";
    private String username;
    private String firstName;
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }
    @Test(priority = 1)
    public void verifyLoginAction() {
        Map<String, ?> body = Map.of("username", "TestUser", "password", "12345");
        Response response = given().body(body).get(USER_LOGIN);
        response.then().statusCode(HttpStatus.SC_OK);
        RestAssured.requestSpecification.sessionId(response.jsonPath().get("message").toString().replaceAll("[^0-9]", ""));
    }
    @Test(priority = 2)
    public void verifyCreateAction() {
        username = Faker.instance().name().username();
        firstName = Faker.instance().name().firstName();
        Map<String, ?> body = Map.of(
                "id", 1, "username", username, "firstName", firstName,
                "lastName", Faker.instance().name().lastName(),
                "email", Faker.instance().internet().emailAddress(),
                "password", Faker.instance().internet().password(),
                "phone", Faker.instance().phoneNumber().phoneNumber(),
                "userStatus", 1
        );
        given().body(body).post(USER).then().statusCode(HttpStatus.SC_OK);
    }
    @Test(priority = 3)
    public void verifyGetAction() {
        given().pathParam("username", username).get(USER_USERNAME)
                .then().statusCode(HttpStatus.SC_OK).and().body("firstName", equalTo(firstName));
    }
    @Test(priority = 4)
    public void verifyDeleteAction() {
        given().pathParam("username", username).delete(USER_USERNAME)
                .then().statusCode(HttpStatus.SC_OK);
    }
    @Test(priority = 5)
    public void verifyLogoutAction() {
        given().get(USER_LOGOUT).then().statusCode(HttpStatus.SC_OK);
    }
}
