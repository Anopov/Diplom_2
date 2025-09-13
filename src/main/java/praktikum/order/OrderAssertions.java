package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.is;

public class OrderAssertions {

    private Ingredients ingredients;
    private OrderResponse orderResponse;
    private OrderUserListResponse orderUserListResponse;

    @Step("Ингридиенты успешно получены")
    public Ingredients getIngredients(ValidatableResponse validatableResponse) {
        validatableResponse.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
        ;
        // Извлекаем оригинальный объект Response
        Response response = validatableResponse.extract().response();

        // Преобразуем тело ответа в объект Ingredients
        IngredientList ingredientList = response.as(IngredientList.class);
        return new Ingredients(ingredientList.getIdIngredients());
    }

    @Step("Успешное создание заказа")
    public OrderResponse orderCreatedSuccessfully(ValidatableResponse validatableResponse){
        validatableResponse.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
        ;
        // Извлекаем оригинальный объект Response
        Response response = validatableResponse.extract().response();

        // Преобразуем тело ответа в объект OrderResponse
        return orderResponse = response.as(OrderResponse.class);
    }

    @Step("Ошибка при создании заказа. Ингридиенты отсутствуют")
    public void orderCreatedFailedEmptyList(ValidatableResponse response){
        response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"))
        ;
    }

    @Step("Ошибка при создании заказа. Некорректные хэштеги ингридиентов")
    public void orderCreatedFailedInvalidIngredients(ValidatableResponse response){
        response.assertThat()
                .statusCode(HTTP_INTERNAL_ERROR)
        ;
    }

    @Step("Успешное получение списка заказов")
    public OrderUserListResponse getOrderListSuccessfully(ValidatableResponse validatableResponse){
        validatableResponse.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
        ;

        // Извлекаем оригинальный объект Response
        Response response = validatableResponse.extract().response();

        // Преобразуем тело ответа в объект Ingredients
        return orderUserListResponse = response.as(OrderUserListResponse.class);
    }

    @Step("Токен отсутствует. Ошибка получения списка заказов")
    public void getOrderListFailly(ValidatableResponse validatableResponse){
        validatableResponse.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"))
        ;
    }
}
