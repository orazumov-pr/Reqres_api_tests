package helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomAllureListener {
    private static final AllureRestAssured allureRestAssured = new AllureRestAssured()
            .setRequestTemplate("request.ftl")
            .setResponseTemplate("response.ftl");

    public static AllureRestAssured getCustomAllureListener() {
        return allureRestAssured;
    }
}
