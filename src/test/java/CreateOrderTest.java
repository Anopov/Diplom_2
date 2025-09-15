import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.order.*;
import praktikum.user.User;
import praktikum.user.UserAssertions;
import praktikum.user.UserClient;
import praktikum.user.UserGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateOrderTest {
    private final UserGenerator generator = new UserGenerator();
    private final User user = generator.random();
    private final UserClient userClient = new UserClient();
    private final UserAssertions userAssertions = new UserAssertions();
    private String accessToken;
    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertions orderAssertions = new OrderAssertions();
    private Ingredients ingredients;

    //Получение списка ингредиентов
    @BeforeEach
    public void getIntgedients() {
        ValidatableResponse createResponse1 = orderClient.getIngredients();
        ingredients = orderAssertions.getIngredients(createResponse1);
    }

    @AfterEach
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse validatableResponse = userClient.delete(accessToken);
            userAssertions.deletedSuccessfully(validatableResponse);
        }
    }

    @Test
    @DisplayName("Успешное создание заказа c авторизацией")
    public void createOrderSuccessWithAuthorizeTest() {
        //создание пользователя
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = userAssertions.createdSuccessfully(createUserResponse).getAccessToken();
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, ingredients);
        OrderResponse orderResponse = orderAssertions.orderCreatedSuccessfully(createResponse);
        Owner ownerOrder = orderResponse.getOrder().getOwner();
        //Проверяем, что у заказа есть владелец и он совпадает с владельцем из токена
        assertEquals(user.getName(), ownerOrder.getName());
        assertEquals(user.getEmail().toLowerCase(), ownerOrder.getEmail());
    }


    @Test
    @DisplayName("Успешное создание заказа без авторизации")
    public void createOrderSuccessWithoutAuthorizeTest() {
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, ingredients);
        OrderResponse orderResponse = orderAssertions.orderCreatedSuccessfully(createResponse);
        Owner ownerOrder = orderResponse.getOrder().getOwner();
        int numberOrder = orderResponse.getOrder().getNumber();
        assertNotEquals(0, numberOrder);
        //Проверяем, что у заказа отсутствует владелец
        assertNull(ownerOrder);
    }

    @Test
    @DisplayName("Успешное создание заказа с ингредиентами")
    public void createOrderSuccessWithIngredientsTest() {
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, ingredients);
        OrderResponse orderResponse = orderAssertions.orderCreatedSuccessfully(createResponse);
        int numberOrder = orderResponse.getOrder().getNumber();
        assertNotEquals(0, numberOrder);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderFailWithoutIngredientsTest() {
        ingredients.setIngredients(Collections.emptyList());
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, ingredients);
        orderAssertions.orderCreatedFailedEmptyList(createResponse);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов. Неверный хэш всех ингридиентов")
    public void createOrderFailWithInvalidIngredientsTest() {
        List<String> invalidHashIngredients = new ArrayList<>();
        for(int i = 0; i < Math.random()*10+1; i++){
            invalidHashIngredients.add(RandomStringUtils.randomAlphanumeric(24));
        }
        ingredients.setIngredients(invalidHashIngredients);
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, ingredients);
        orderAssertions.orderCreatedFailedInvalidIngredients(createResponse);
    }


}
