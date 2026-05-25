package api;

import config.ApiConfig;
import models.AuthorizationBodyModel;
import models.AuthorizationErrorModel;
import models.AuthorizationResponseModel;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class AuthorizationApi {

    private static final String REGISTER_ENDPOINT = "/register";
    private static final String LOGIN_ENDPOINT = "/login";
    private final String apiKey = ApiConfig.getInstance().apiKey();

    public AuthorizationResponseModel successRegister(AuthorizationBodyModel requestData) {
        return given()
                .spec(baseRequestSpec())
                .header("x-api-key", apiKey)
                .body(requestData)
                .when()
                .post(REGISTER_ENDPOINT)
                .then()
                .spec(successResponseSpec())
                .extract()
                .as(AuthorizationResponseModel.class);
    }

    public AuthorizationResponseModel successLogin(AuthorizationBodyModel requestData) {
        return given()
                .spec(baseRequestSpec())
                .header("x-api-key", apiKey)
                .body(requestData)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .spec(successResponseSpec())
                .extract()
                .as(AuthorizationResponseModel.class);
    }

    public AuthorizationErrorModel errorRegister(AuthorizationBodyModel requestData) {
        return given()
                .spec(baseRequestSpec())
                .header("x-api-key", apiKey)
                .body(requestData)
                .when()
                .post(REGISTER_ENDPOINT)
                .then()
                .spec(badRequestResponseSpec())
                .extract()
                .as(AuthorizationErrorModel.class);
    }

    public AuthorizationErrorModel errorLogin(AuthorizationBodyModel requestData) {
        return given()
                .spec(baseRequestSpec())
                .header("x-api-key", apiKey)
                .body(requestData)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .spec(badRequestResponseSpec())
                .extract()
                .as(AuthorizationErrorModel.class);
    }
}