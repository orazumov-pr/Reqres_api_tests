package api;

import models.ResourceResponseModel;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class ResourcesApi {

    private static final String RESOURCES_ENDPOINT = "/unknown";

    public Response getListResources() {
        return given()
                .spec(baseRequestSpec())
                .when()
                .get(RESOURCES_ENDPOINT)
                .then()
                .spec(successResponseSpec())
                .extract()
                .response();
    }

    public Response getListResources(int page) {
        return given()
                .spec(baseRequestSpec())
                .queryParam("page", page)
                .when()
                .get(RESOURCES_ENDPOINT)
                .then()
                .spec(successResponseSpec())
                .extract()
                .response();
    }

    public ResourceResponseModel getSingleResource(int resourceId) {
        return given()
                .spec(baseRequestSpec())
                .when()
                .get(RESOURCES_ENDPOINT + "/" + resourceId)
                .then()
                .spec(successResponseSpec())
                .extract()
                .as(ResourceResponseModel.class);
    }

    public Response getSingleResourceNotFound(int resourceId) {
        return given()
                .spec(baseRequestSpec())
                .when()
                .get(RESOURCES_ENDPOINT + "/" + resourceId)
                .then()
                .spec(notFoundResponseSpec())
                .extract()
                .response();
    }
}
