package tests;

import config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    protected static ApiConfig apiConfig = ApiConfig.getInstance();

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = apiConfig.baseUrl();
        RestAssured.basePath = apiConfig.basePath();

        // Добавляем API ключ в глобальные заголовки
        RestAssured.useRelaxedHTTPSValidation();

        // Опционально: добавляем глобальный фильтр для API ключа
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}