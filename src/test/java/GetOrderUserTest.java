import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.order.Ingredients;
import praktikum.order.OrderAssertions;
import praktikum.order.OrderClient;
import praktikum.order.OrderUserListResponse;
import praktikum.user.User;
import praktikum.user.UserAssertions;
import praktikum.user.UserClient;
import praktikum.user.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetOrderUserTest {
    private final UserGenerator generator = new UserGenerator();
    private final User user = generator.random();
    private final UserClient userClient = new UserClient();
    private final UserAssertions userAssertions = new UserAssertions();
    private String accessToken;
    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertions orderAssertions = new OrderAssertions();
    private Ingredients ingredients;
    private OrderUserListResponse orderUserListResponse;

    //Получение списка ингредиентов
    @BeforeEach
    public void getIntgedients() {
        ValidatableResponse createResponse1 = orderClient.getIngredients();
        ingredients = orderAssertions.getIngredients(createResponse1);
    }

//    @AfterEach
//    public void deleteUser() {
//        if (accessToken != null) {
//            ValidatableResponse validatableResponse = userClient.delete(accessToken);
//            userAssertions.deletedSuccessfully(validatableResponse);
//        }
//    }

    @Test
    @DisplayName("Получение списка заказов. Пользователь авторизован")
    public void getUserOrderSuccessTest() {
        //Создаем пользователя и сохраняем токен
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = userAssertions.createdSuccessfully(createUserResponse).getAccessToken();
        //Оформляем случайное количество заказов
        int countOrder = (int) (Math.random()*10+1);
        int counter = 0;
        while (counter < countOrder) {
            ValidatableResponse createResponse = orderClient.createOrder(accessToken, ingredients);
            counter++;
        }
        ValidatableResponse createResponse = orderClient.getOrders(accessToken);
        orderUserListResponse = orderAssertions.getOrderListSuccessfully(createResponse);
        System.out.println(orderUserListResponse.getOrders().size());
        int actualOrder = orderUserListResponse.getOrders().size();
        //Проверяем количество заказов
        assertEquals(countOrder, actualOrder);
    }

    @Test
    @DisplayName("Получение списка заказов. Пользователь неавторизован")
    public void getUserOrderFailTest() {
        ValidatableResponse createResponse = orderClient.getOrders(accessToken);
        orderAssertions.getOrderListFailly(createResponse);

    }
}
