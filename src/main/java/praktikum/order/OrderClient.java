package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import praktikum.Client;

public class OrderClient extends Client {

    @Step("Создание заказа")
    public ValidatableResponse createOrder(String accessToken, Ingredients ingredients) {
        RequestSpecification requestSpec = spec(); // Получаем общую спецификацию

        // Проверяем наличие токена и добавляем его в заголовок
        if (accessToken != null) {
            requestSpec.header("Authorization", accessToken);
        }
        return requestSpec
                .body(ingredients)
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Получение списка ингредиентов")
    public ValidatableResponse getIngredients() {
        return spec()
                .when()
                .get("/ingredients")
                .then().log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrders(String accessToken) {
        RequestSpecification requestSpec = spec(); // Получаем общую спецификацию

        // Проверяем наличие токена и добавляем его в заголовок
        if (accessToken != null) {
            requestSpec.header("Authorization", accessToken);
        }
        return requestSpec
                .when()
                .get("/orders")
                .then().log().all();
    }
}
