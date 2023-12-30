package tests;

import models.lombok.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import specs.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqResInTests {
    @Test
    @Tag("api_test")
    void checkUsersListWithCustomAllureAndAllureStepsAndSpecsTest() {
        UsersListSpec spec = new UsersListSpec();
        ListUsersResponseLombokModel listUsersResponse =
        step("Getting users list", () ->
                given(spec.usersListRequestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(spec.usersListResponseSpec)
                .extract().as(ListUsersResponseLombokModel.class));

        step("Verifying a page number", () -> {
            assertThat(listUsersResponse.getPage()).isEqualTo(2);
        });
        step("Verifying a number of user per page", () -> {
            assertThat(listUsersResponse.getPerPage()).isEqualTo(6);
        });
        step("Verifying users total", () -> {
            assertThat(listUsersResponse.getTotal()).isEqualTo(12);
        });
        step("Verifying users total per page", () -> {
            assertThat(listUsersResponse.getTotalPages()).isEqualTo(2);
        });
        step("Verifying users list size", () -> {
            assertThat(listUsersResponse.getData().size()).isEqualTo(6);
        });
    }

    @Test
    @Tag("api_test")
    void checkSingleUserTest() {
        DataModel user = new DataModel();
        user.setId(2);
        user.setEmail("janet.weaver@reqres.in");
        user.setFirstName("Janet");
        user.setLastName("Weaver");
        user.setAvatar("https://reqres.in/img/faces/2-image.jpg");

        SingleUserSpec spec = new SingleUserSpec();
        SingleUserResponseModel singleUserResponse =
                step("Getting a single user", () ->
                        given(spec.singleUserRequestSpec)
                .when()
                .get("/users/" + user.getId())
                .then()
                .spec(spec.singleUserResponseSpec)
                .extract().as(SingleUserResponseModel.class));

        step("Verifying a user id", () -> {
            assertThat(singleUserResponse.getData().getId()).isEqualTo(user.getId());
        });
        step("Verifying a user email", () -> {
            assertThat(singleUserResponse.getData().getEmail()).isEqualTo(user.getEmail());
        });
        step("Verifying a user first name", () -> {
            assertThat(singleUserResponse.getData().getFirstName()).isEqualTo(user.getFirstName());
        });
        step("Verifying a user last name", () -> {
            assertThat(singleUserResponse.getData().getLastName()).isEqualTo(user.getLastName());
        });
        step("Verifying a user avatar", () -> {
            assertThat(singleUserResponse.getData().getAvatar()).isEqualTo(user.getAvatar());
        });
    }

    @Test
    @Tag("api_test")
    void checkSingleUserNotFoundTest() {
        DataModel user = new DataModel();
        user.setId(23);

        SingleUserNotFoundSpec spec = new SingleUserNotFoundSpec();
        SingleUserResponseModel singleUserResponse =
                step("Getting a non-existing single user", () ->
                        given(spec.singleUserNotFoundRequestSpec)
                                .when()
                                .get("/users/" + user.getId())
                                .then()
                                .spec(spec.singleUserNotFoundResponseSpec)
                                .extract().as(SingleUserResponseModel.class));

        step("Verifying an empty response", () -> {
            assertThat(singleUserResponse.getData()).isNull();
        });
    }

    @Test
    @Tag("api_test")
    void createNewUserTest() {
        String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());

        NewUserModel user = new NewUserModel();
        user.setName("bill");
        user.setJob("teacher");

        CreateUserSpec spec = new CreateUserSpec();
        CreateNewUserResponseModel createNewUserResponse =
                step("Creating a new user", () ->
                        given(spec.createUserRequestSpec)
                        .body(user)
                        .when().post("/users")
                        .then()
                        .spec(spec.createUserResponseSpec)
                        .extract().as(CreateNewUserResponseModel.class));

        step("Verifying a user name", () -> {
            assertThat(createNewUserResponse.getName()).isEqualTo(user.getName());
        });
        step("Verifying a user job", () -> {
            assertThat(createNewUserResponse.getJob()).isEqualTo(user.getJob());
        });
        step("Verifying time when a user has been created", () -> {
            assertThat(createNewUserResponse.getCreatedAt()).contains(dateTime);
        });
    }

    @Test
    @Tag("api_test")
    void updateUserTest() {
        String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());

        NewUserModel user = new NewUserModel();
        user.setName("morpheus");
        user.setJob("zion president");

        UpdateUserSpec spec = new UpdateUserSpec();
        UpdateUserResponseModel updateUserResponse =
                step("Updating an existing user", () ->
                        given(spec.updateUserRequestSpec)
                                .body(user)
                                .when()
                                .put("/users/2")
                                .then()
                                .spec(spec.updateUserResponseSpec)
                                .extract().as(UpdateUserResponseModel.class));

        step("Verifying a user name", () -> {
            assertThat(updateUserResponse.getName()).isEqualTo(user.getName());
        });
        step("Verifying a user job", () -> {
            assertThat(updateUserResponse.getJob()).isEqualTo(user.getJob());
        });
        step("Verifying time when a user has been updated", () -> {
            assertThat(updateUserResponse.getUpdatedAt()).contains(dateTime);
        });
    }
}
