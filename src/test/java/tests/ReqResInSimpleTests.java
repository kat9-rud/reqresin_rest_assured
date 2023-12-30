package tests;

import models.lombok.ListUsersResponseLombokModel;
import models.pojo.ListUsersResponsePojoModel;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class ReqResInSimpleTests {
    @Test
    void checkUsersListTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(2))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2))
                .body("data", hasSize(6));
    }

    @Test
    void checkUsersListWithPojoModelTest() {
        ListUsersResponsePojoModel listUsersResponse = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ListUsersResponsePojoModel.class);

        assertThat(listUsersResponse.getPage()).isEqualTo(2);
        assertThat(listUsersResponse.getPer_page()).isEqualTo(6);
        assertThat(listUsersResponse.getTotal()).isEqualTo(12);
        assertThat(listUsersResponse.getTotal_pages()).isEqualTo(2);
        assertThat(listUsersResponse.getData().size()).isEqualTo(6);
    }

    @Test
    void checkUsersListWithLombokModelTest() {
        ListUsersResponseLombokModel listUsersResponse = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ListUsersResponseLombokModel.class);

        assertThat(listUsersResponse.getPage()).isEqualTo(2);
        assertThat(listUsersResponse.getPerPage()).isEqualTo(6);
        assertThat(listUsersResponse.getTotal()).isEqualTo(12);
        assertThat(listUsersResponse.getTotalPages()).isEqualTo(2);
        assertThat(listUsersResponse.getData().size()).isEqualTo(6);
    }

    @Test
    void checkSingleUserTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2));
    }

    @Test
    void checkSingleUserNotFoundTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .body("", equalTo(Collections.emptyMap()));
    }

    @Test
    void createNewUserTest() {
        String name = "bill";
        String job = "teacher";
        String data = "{ \"name\": \"" + name + "\", \"job\": \"" + job + "\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is(name))
                .body("job", is(job));
    }

    @Test
    void updateUserTest() {
        String name = "bill";
        String job = "president";
        String data = "{ \"name\": \"" + name + "\", \"job\": \"" + job + "\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is(name))
                .body("job", is(job));
    }
}
