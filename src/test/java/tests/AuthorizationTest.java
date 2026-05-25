package tests;

import api.AuthorizationApi;
import config.AuthConfig;
import io.qameta.allure.*;
import models.AuthorizationBodyModel;
import models.AuthorizationErrorModel;
import models.AuthorizationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.AllureSteps.step;

@Epic("Тестирование API приложения Reqres.in")
@Feature("Авторизация и регистрация")
@Story("Авторизация пользователей")
public class AuthorizationTest extends TestBase {

    protected AuthConfig authConfig = AuthConfig.getInstance();
    protected AuthorizationApi authorizationApi = new AuthorizationApi();

    String authEmail = authConfig.authEmail();
    String authPassword = authConfig.authPassword();
    String authUndefinedEmail = authConfig.authUndefinedEmail();
    String authUndefinedPassword = authConfig.authUndefinedPassword();

    @Test
    @Tag("Smoke")
    @Tag("Authorization")
    @DisplayName("Проверка успешной регистрации с Email и Password")
    void successfulRegistrationTest() {
        final AuthorizationResponseModel successfulRegisterResponse = step("Выполнение успешной регистрации с Email и Password", () -> {
            AuthorizationBodyModel requestData = new AuthorizationBodyModel(authEmail, authPassword);
            return authorizationApi.successRegister(requestData);
        });

        step("Проверка ответа с токеном на запрос об успешной регистрации", () -> {
            assertThat(successfulRegisterResponse.token())
                    .as("Значение полученного токена из ответа верное")
                    .isNotEmpty()
                    .isNotBlank();
        });
    }

    @Test
    @Tag("Smoke")
    @Tag("Authorization")
    @DisplayName("Проверка успешной авторизации с Email и Password")
    void successfulAuthorizationTest() {
        final AuthorizationResponseModel successfulAuthResponse = step("Выполнение успешной авторизации с Email и Password", () -> {
            AuthorizationBodyModel requestData = new AuthorizationBodyModel(authEmail, authPassword);
            return authorizationApi.successLogin(requestData);
        });

        step("Проверка ответа с токеном на запрос об успешной авторизации", () -> {
            assertThat(successfulAuthResponse.token())
                    .as("Значение полученного токена из ответа верное")
                    .isEqualTo("QpwL5tke4Pnpja7X4");
        });
    }


    @Test
    @Tag("Regression")
    @Tag("Authorization")
    @DisplayName("Проверка неуспешной регистрации без Email")
    void registrationWithoutEmailTest() {
        final AuthorizationErrorModel errorRegisterWithoutEmailResponse = step("Выполнение неуспешной регистрации без Email", () -> {
            AuthorizationBodyModel requestData = new AuthorizationBodyModel(null, authPassword);
            return authorizationApi.errorRegister(requestData);
        });

        step("Проверка ответа с ошибкой регистрации", () -> {
            assertThat(errorRegisterWithoutEmailResponse.error())
                    .as("Верный текст с ошибкой в ответе")
                    .isEqualTo("Missing email or username");
        });
    }


    @Test
    @Tag("Smoke")
    @Tag("Authorization")
    @DisplayName("Проверка неуспешной авторизации без Email")
    void authorizationWithoutEmailTest() {
        final AuthorizationErrorModel errorAuthWithoutEmailResponse = step("Выполнение неуспешной авторизации без Email", () -> {
            AuthorizationBodyModel requestData = new AuthorizationBodyModel(null, authPassword);
            return authorizationApi.errorLogin(requestData);
        });

        step("Проверка ответа с ошибкой авторизации", () -> {
            assertThat(errorAuthWithoutEmailResponse.error())
                    .as("Верный текст с ошибкой в ответе")
                    .isEqualTo("Missing email or username");
        });
    }


    @Test
    @Tag("Regression")
    @Tag("Authorization")
    @DisplayName("Проверка неуспешной регистрации без Password")
    void registrationWithoutPasswordTest() {
        final AuthorizationErrorModel errorRegisterWithoutPasswordResponse = step("Выполнение неуспешной регистрации без Password", () -> {
            AuthorizationBodyModel requestData = new AuthorizationBodyModel(authEmail, null);
            return authorizationApi.errorRegister(requestData);
        });

        step("Проверка ответа с ошибкой регистрации", () -> {
            assertThat(errorRegisterWithoutPasswordResponse.error())
                    .as("Верный текст с ошибкой в ответе")
                    .isEqualTo("Missing password");
        });
    }


    @Test
    @Tag("Smoke")
    @Tag("Authorization")
    @DisplayName("Проверка неуспешной авторизации без Password")
    void authorizationWithoutPasswordTest() {
        final AuthorizationErrorModel errorAuthWithoutPasswordResponse = step("Выполнение неуспешной авторизации без Password", () -> {
            AuthorizationBodyModel requestData = new AuthorizationBodyModel(authEmail, null);
            return authorizationApi.errorLogin(requestData);
        });

        step("Проверка ответа с ошибкой авторизации", () -> {
            assertThat(errorAuthWithoutPasswordResponse.error())
                    .as("Верный текст с ошибкой в ответе")
                    .isEqualTo("Missing password");
        });
    }


    @Test
    @Tag("Regression")
    @Tag("Authorization")
    @DisplayName("Проверка неуспешной регистрации с данными неизвестного пользователя")
    void undefinedUserRegistrationTest() {
        final AuthorizationErrorModel errorRegisterWithUndefinedData = step("Выполнение неуспешной регистрации с данными неизвестного пользователя", () -> {
            AuthorizationBodyModel requestData = new AuthorizationBodyModel(authUndefinedEmail, authUndefinedPassword);
            return authorizationApi.errorRegister(requestData);
        });

        step("Проверка ответа с ошибкой регистрации", () -> {
            assertThat(errorRegisterWithUndefinedData.error())
                    .as("Верный текст с ошибкой в ответе")
                    .isEqualTo("Note: Only defined users succeed registration");
        });
    }


    @Test
    @Tag("Smoke")
    @Tag("Authorization")
    @DisplayName("Проверка неуспешной авторизации с данными неизвестного пользователя")
    void undefinedUserAuthorizationTest() {
        final AuthorizationErrorModel errorAuthWithUndefinedData = step("Выполнение неуспешной авторизации с данными неизвестного пользователя", () -> {
            AuthorizationBodyModel requestData = new AuthorizationBodyModel(authUndefinedEmail, authUndefinedPassword);
            return authorizationApi.errorLogin(requestData);
        });

        step("Проверка ответа с ошибкой авторизации", () -> {
            assertThat(errorAuthWithUndefinedData.error())
                    .as("Верный текст с ошибкой в ответе")
                    .isEqualTo("user not found");
        });
    }

}
