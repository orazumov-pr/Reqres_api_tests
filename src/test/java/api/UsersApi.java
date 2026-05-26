package api;

import models.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class UsersApi {

    private static final String USERS_ENDPOINT = "/users";

    public ListUsersResponseModel getListUsers(int page) {
        return given()
                .spec(baseRequestSpec())
                .queryParam("page", page)
                .when()
                .get(USERS_ENDPOINT)
                .then()
                .spec(successResponseSpec())
                .extract()
                .as(ListUsersResponseModel.class);
    }

    public ListUsersResponseModel getListUsersWithDelay(int page, int delay) {
        return given()
                .spec(baseRequestSpec())
                .queryParam("page", page)
                .queryParam("delay", delay)
                .when()
                .get(USERS_ENDPOINT)
                .then()
                .spec(successResponseSpec())
                .extract()
                .as(ListUsersResponseModel.class);
    }

    public SingleUserResponseModel getSingleUser(int userId) {
        return given()
                .spec(baseRequestSpec())
                .when()
                .get(USERS_ENDPOINT + "/" + userId)
                .then()
                .spec(successResponseSpec())
                .extract()
                .as(SingleUserResponseModel.class);
    }

    public Response getSingleUserNotFound(int userId) {
        return given()
                .spec(baseRequestSpec())
                .when()
                .get(USERS_ENDPOINT + "/" + userId)
                .then()
                .spec(notFoundResponseSpec())
                .extract()
                .response();
    }

    public CreateUserResponseModel createUser(CreateUserRequestModel requestData) {
        return given()
                .spec(baseRequestSpec())
                .body(requestData)
                .when()
                .post(USERS_ENDPOINT)
                .then()
                .spec(createdResponseSpec())
                .extract()
                .as(CreateUserResponseModel.class);
    }

    public UpdateUserResponseModel updateUser(int userId, UpdateUserRequestModel requestData) {
        return given()
                .spec(baseRequestSpec())
                .body(requestData)
                .when()
                .put(USERS_ENDPOINT + "/" + userId)
                .then()
                .spec(successResponseSpec())
                .extract()
                .as(UpdateUserResponseModel.class);
    }

    public UpdateUserResponseModel patchUser(int userId, UpdateUserRequestModel requestData) {
        return given()
                .spec(baseRequestSpec())
                .body(requestData)
                .when()
                .patch(USERS_ENDPOINT + "/" + userId)
                .then()
                .spec(successResponseSpec())
                .extract()
                .as(UpdateUserResponseModel.class);
    }

    public Response deleteUser(int userId) {
        return given()
                .spec(baseRequestSpec())
                .when()
                .delete(USERS_ENDPOINT + "/" + userId)
                .then()
                .spec(noContentResponseSpec())
                .extract()
                .response();
    }
}
