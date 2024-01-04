package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static helpers.CustomAllureListener.withCustomTemplates;
import static helpers.RequestVerificationTokenExtractor.extractTokens;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class DemowebshopTests {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
    }

    @Test
    void updateUserInfoTest() {
        String login = "qwerty111@qwerty.com";
        String password = "qwerty123";

        String authorizationCookie = step("Get an auth cookie", () -> {
            return
                    given()
                            .filter(withCustomTemplates())
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .formParam("Email", login)
                            .formParam("Password", password)
                            .when()
                            .post("/login")
                            .then()
                            .statusCode(302)
                            .extract()
                            .cookie("NOPCOMMERCE.AUTH");
        });

        List<String> tokens = step("Get two tokens", () -> {
            return extractTokens(RestAssured.baseURI + "/customer/info",
                    "NOPCOMMERCE.AUTH", authorizationCookie);
        });

        String body = "__RequestVerificationToken=" + tokens.get(1) +
                "&FirstName=Olga&" +
                "LastName=Smith&Email=qwerty111@qwerty.com&save-info-button=Save";

        Response response = step("update", () -> {
            return
                    given()
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .cookie("NOPCOMMERCE.AUTH", authorizationCookie)
                            .cookie("__RequestVerificationToken", tokens.get(0))
                            .body(body)
                            .when()
                            .post("/customer/info");
        });

        step("verification", () -> {
            response
                    .then()
                    .log().all()
                    .statusCode(302);

            String responseBody = response.getBody().asString();

            String expectedString1 = "<html><head><title>Object moved</title></head><body>";
            String expectedString2 = "<h2>Object moved to <a href=\"/customer/info\">here</a>.</h2>";
            String expectedString3 = "</body></html>";

            assertThat(responseBody, StringContains.containsString(expectedString1));
            assertThat(responseBody, StringContains.containsString(expectedString2));
            assertThat(responseBody, StringContains.containsString(expectedString3));
        });
    }
}