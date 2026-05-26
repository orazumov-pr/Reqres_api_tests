package tests;

import api.UsersApi;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.AllureSteps.step;


public class UsersTest extends TestBase {

    protected UsersApi usersApi = new UsersApi();
    private final Faker faker = new Faker();

    @Test
    @DisplayName("Проверка получения списка пользователей")
    void getListUsersTest() {
        final ListUsersResponseModel response = step("Выполнение запроса на получение списка пользователей со 2-й страницы", () ->
                usersApi.getListUsers(2)
        );

        step("Проверка структуры ответа", () -> {
            assertThat(response.page())
                    .as("Номер страницы должен быть 2")
                    .isEqualTo(2);
            assertThat(response.perPage())
                    .as("Количество пользователей на странице должно быть 6")
                    .isEqualTo(6);
            assertThat(response.total())
                    .as("Общее количество пользователей должно быть 12")
                    .isEqualTo(12);
            assertThat(response.totalPages())
                    .as("Общее количество страниц должно быть 2")
                    .isEqualTo(2);
            assertThat(response.data())
                    .as("Список пользователей не должен быть пустым")
                    .isNotEmpty();
            assertThat(response.support())
                    .as("Объект support не должен быть null")
                    .isNotNull();
        });
    }

    @Test
    @DisplayName("Проверка получения первого пользователя")
    void getFirstUserTest() {
        final SingleUserResponseModel response = step("Выполнение запроса на получение пользователя с ID 1", () ->
                usersApi.getSingleUser(1)
        );

        step("Проверка данных пользователя", () -> {
            assertThat(response.data().id())
                    .as("ID пользователя должен быть 1")
                    .isEqualTo(1);
            assertThat(response.data().email())
                    .as("Email пользователя должен соответствовать ожидаемому")
                    .isEqualTo("george.bluth@reqres.in");
            assertThat(response.data().firstName())
                    .as("First name должен быть George")
                    .isEqualTo("George");
            assertThat(response.data().lastName())
                    .as("Last name должен быть Bluth")
                    .isEqualTo("Bluth");
            assertThat(response.data().avatar())
                    .as("Avatar URL должен содержать корректный путь")
                    .contains("https://reqres.in/img/faces/1-image.jpg");
            assertThat(response.support())
                    .as("Объект support не должен быть null")
                    .isNotNull();
            assertThat(response.support().url())
                    .as("Support URL не должен быть пустым")
                    .isNotEmpty();
        });
    }

    @Test
    @DisplayName("Проверка получения пользователя по ID 2")
    void getSingleUserTest() {
        final SingleUserResponseModel response = step("Выполнение запроса на получение пользователя с ID 2", () ->
                usersApi.getSingleUser(2)
        );

        step("Проверка данных пользователя", () -> {
            assertThat(response.data().id())
                    .as("ID пользователя должен быть 2")
                    .isEqualTo(2);
            assertThat(response.data().email())
                    .as("Email пользователя должен соответствовать ожидаемому")
                    .isEqualTo("janet.weaver@reqres.in");
            assertThat(response.data().firstName())
                    .as("First name должен быть Janet")
                    .isEqualTo("Janet");
            assertThat(response.data().lastName())
                    .as("Last name должен быть Weaver")
                    .isEqualTo("Weaver");
            assertThat(response.support())
                    .as("Объект support не должен быть null")
                    .isNotNull();
        });
    }

    @Test
    @DisplayName("Проверка получения несуществующего пользователя")
    void getUserNotFoundTest() {
        step("Выполнение запроса на получение несуществующего пользователя с ID 23", () -> {
            var response = usersApi.getSingleUserNotFound(23);
            assertThat(response.getStatusCode())
                    .as("Статус код должен быть 404 Not Found")
                    .isEqualTo(404);
        });
    }

    @Test
    @DisplayName("Проверка создания нового пользователя")
    void createUserTest() {
        String name = faker.name().fullName();
        String job = faker.job().title();

        final CreateUserResponseModel response = step("Выполнение запроса на создание пользователя", () -> {
            CreateUserRequestModel requestData = new CreateUserRequestModel(name, job);
            return usersApi.createUser(requestData);
        });

        step("Проверка данных созданного пользователя", () -> {
            assertThat(response.name())
                    .as("Имя пользователя должно соответствовать отправленному")
                    .isEqualTo(name);
            assertThat(response.job())
                    .as("Должность пользователя должна соответствовать отправленной")
                    .isEqualTo(job);
            assertThat(response.id())
                    .as("ID пользователя не должен быть пустым")
                    .isNotEmpty();
            assertThat(response.createdAt())
                    .as("Дата создания не должна быть пустой")
                    .isNotEmpty();
        });
    }

    @Test
    @DisplayName("Проверка обновления пользователя через PUT")
    void updateUserPutTest() {
        String newName = faker.name().fullName();
        String newJob = faker.job().title();

        final UpdateUserResponseModel response = step("Выполнение запроса на обновление пользователя (PUT)", () -> {
            UpdateUserRequestModel requestData = new UpdateUserRequestModel(newName, newJob);
            return usersApi.updateUser(2, requestData);
        });

        step("Проверка обновленных данных", () -> {
            assertThat(response.name())
                    .as("Имя пользователя должно обновиться")
                    .isEqualTo(newName);
            assertThat(response.job())
                    .as("Должность пользователя должна обновиться")
                    .isEqualTo(newJob);
            assertThat(response.updatedAt())
                    .as("Дата обновления не должна быть пустой")
                    .isNotEmpty();
        });
    }

    @Test
    @DisplayName("Проверка частичного обновления пользователя через PATCH")
    void updateUserPatchTest() {
        String newJob = faker.job().title();

        final UpdateUserResponseModel response = step("Выполнение запроса на частичное обновление пользователя (PATCH)", () -> {
            UpdateUserRequestModel requestData = new UpdateUserRequestModel(null, newJob);
            return usersApi.patchUser(2, requestData);
        });

        step("Проверка частично обновленных данных", () -> {
            assertThat(response.job())
                    .as("Должность пользователя должна обновиться")
                    .isEqualTo(newJob);
            assertThat(response.updatedAt())
                    .as("Дата обновления не должна быть пустой")
                    .isNotEmpty();
        });
    }

    @Test
    @DisplayName("Проверка удаления пользователя")
    void deleteUserTest() {
        step("Выполнение запроса на удаление пользователя с ID 2", () -> {
            var response = usersApi.deleteUser(2);
            assertThat(response.getStatusCode())
                    .as("При успешном удалении статус код должен быть 204")
                    .isEqualTo(204);
        });
    }

}