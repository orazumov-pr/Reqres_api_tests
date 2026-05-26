package tests;

import api.ResourcesApi;
import io.qameta.allure.*;
import models.ResourceResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.AllureSteps.step;

@Epic("Тестирование API приложения Reqres.in")
@Feature("Работа с ресурсами")
@Story("Получение информации о ресурсах (цветах)")
public class ResourcesTest extends TestBase {

    protected ResourcesApi resourcesApi = new ResourcesApi();

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Tag("Smoke")
    @Tag("Resources")
    @DisplayName("Проверка получения списка всех ресурсов")
    void getListResourcesTest() {
        step("Выполнение запроса на получение списка ресурсов", () -> {
            var response = resourcesApi.getListResources();

            assertThat(response.getStatusCode())
                    .as("Статус код должен быть 200 OK")
                    .isEqualTo(200);

            assertThat(response.jsonPath().getList("data"))
                    .as("Список ресурсов не должен быть пустым")
                    .isNotEmpty();

            assertThat(response.jsonPath().getInt("page"))
                    .as("Должна быть первая страница")
                    .isEqualTo(1);

            assertThat(response.jsonPath().getInt("per_page"))
                    .as("На странице должно быть 6 ресурсов")
                    .isEqualTo(6);
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Tag("Smoke")
    @Tag("Resources")
    @DisplayName("Проверка получения ресурса по ID 1")
    void getSingleResourceTest() {
        final ResourceResponseModel response = step("Выполнение запроса на получение ресурса с ID 1", () ->
                resourcesApi.getSingleResource(1)
        );

        step("Проверка данных ресурса", () -> {
            assertThat(response.data().id())
                    .as("ID ресурса должен быть 1")
                    .isEqualTo(1);
            assertThat(response.data().name())
                    .as("Название ресурса должно быть 'cerulean'")
                    .isEqualTo("cerulean");
            assertThat(response.data().year())
                    .as("Год должен быть 2000")
                    .isEqualTo(2000);
            assertThat(response.data().color())
                    .as("Цвет должен быть '#98B2D1'")
                    .isEqualTo("#98B2D1");
            assertThat(response.data().pantoneValue())
                    .as("Pantone value должен быть '15-4020'")
                    .isEqualTo("15-4020");
            assertThat(response.support())
                    .as("Объект support не должен быть null")
                    .isNotNull();
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Tag("Smoke")
    @Tag("Resources")
    @DisplayName("Проверка получения ресурса по ID 2")
    void getResourceById2Test() {
        final ResourceResponseModel response = step("Выполнение запроса на получение ресурса с ID 2", () ->
                resourcesApi.getSingleResource(2)
        );

        step("Проверка данных ресурса", () -> {
            assertThat(response.data().id())
                    .as("ID ресурса должен быть 2")
                    .isEqualTo(2);
            assertThat(response.data().name())
                    .as("Название ресурса должно быть 'fuchsia rose'")
                    .isEqualTo("fuchsia rose");
            assertThat(response.data().year())
                    .as("Год должен быть 2001")
                    .isEqualTo(2001);
            assertThat(response.data().color())
                    .as("Цвет должен быть '#C74375'")
                    .isEqualTo("#C74375");
            assertThat(response.support())
                    .as("Объект support не должен быть null")
                    .isNotNull();
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Tag("Smoke")
    @Tag("Resources")
    @DisplayName("Проверка получения ресурса по ID 3")
    void getResourceById3Test() {
        final ResourceResponseModel response = step("Выполнение запроса на получение ресурса с ID 3", () ->
                resourcesApi.getSingleResource(3)
        );

        step("Проверка данных ресурса", () -> {
            assertThat(response.data().id())
                    .as("ID ресурса должен быть 3")
                    .isEqualTo(3);
            assertThat(response.data().name())
                    .as("Название ресурса должно быть 'true red'")
                    .isEqualTo("true red");
            assertThat(response.data().year())
                    .as("Год должен быть 2002")
                    .isEqualTo(2002);
            assertThat(response.data().color())
                    .as("Цвет должен быть '#BF1932'")
                    .isEqualTo("#BF1932");
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Tag("Regression")
    @Tag("Resources")
    @DisplayName("Проверка получения несуществующего ресурса")
    void getResourceNotFoundTest() {
        step("Выполнение запроса на получение несуществующего ресурса с ID 23", () -> {
            var response = resourcesApi.getSingleResourceNotFound(23);

            assertThat(response.getStatusCode())
                    .as("Статус код должен быть 404 Not Found")
                    .isEqualTo(404);

            assertThat(response.jsonPath().getString("error"))
                    .as("Сообщение об ошибке должно соответствовать")
                    .contains("Not Found");
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Tag("Smoke")
    @Tag("Resources")
    @DisplayName("Проверка пагинации ресурсов")
    void getResourcesPaginationTest() {
        step("Выполнение запроса на получение второй страницы ресурсов", () -> {
            var response = resourcesApi.getListResources(2);

            assertThat(response.getStatusCode())
                    .as("Статус код должен быть 200 OK")
                    .isEqualTo(200);

            assertThat(response.jsonPath().getInt("page"))
                    .as("Должна быть вторая страница")
                    .isEqualTo(2);

            assertThat(response.jsonPath().getList("data"))
                    .as("Список ресурсов должен быть пустым (всего 6 ресурсов)")
                    .isEmpty();
        });
    }
}
