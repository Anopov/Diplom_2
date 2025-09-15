package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import praktikum.Client;

public class UserClient extends Client {

    private final String API_PREFIX = "/auth";

    @Step("Создание пользователя")
    public ValidatableResponse create(User user) {
        return spec()
                .body(user)
                .when()
                .post(API_PREFIX + "/register")
                .then().log().all();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse login(Credentials credentials) {
        return spec()
                .body(credentials)
                .when()
                .post(API_PREFIX + "/login")
                .then().log().all();
    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse changeUser(String accessToken, User user) {
        RequestSpecification requestSpec = spec(); // Получаем общую спецификацию

        // Проверяем наличие токена и добавляем его в заголовок
        if (accessToken != null) {
            requestSpec.header("Authorization", accessToken);
        }

        return requestSpec
                .body(user)
                .when()
                .patch(API_PREFIX + "/user")
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(API_PREFIX + "/user")
                .then().log().all();
    }
}
