import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.user.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeDataUserTest {
    private final UserGenerator generator = new UserGenerator();
    private final User user = generator.random();
    private final UserClient userClient = new UserClient();
    private final UserAssertions userAssertions = new UserAssertions();
    private String accessToken;
    private User userData;

    @AfterEach
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse validatableResponse = userClient.delete(accessToken);
            userAssertions.deletedSuccessfully(validatableResponse);
        }
    }

    @Test
    @DisplayName("Успешное изменение email пользователя")
    public void changeEmailSuccessUserTest() {
        ValidatableResponse createResponse = userClient.create(user);
        UserResponse userBeforeChange = userAssertions.createdSuccessfully(createResponse);
        accessToken = userBeforeChange.getAccessToken();
        userData = userBeforeChange.getUser();
        userData.setEmail(RandomStringUtils.randomAlphanumeric(5) + "@yandex.ru");
        ValidatableResponse createResponseChangeUser = userClient.changeUser(accessToken, userData);
        UserResponse userAfterChange = userAssertions.changeUserSuccess(createResponseChangeUser);

        assertEquals(
                userData.getEmail().toLowerCase(),
                userAfterChange.getUser().getEmail(),
                "Новый email не совпадает с ожидаемым значением!"
        );
    }

    @Test
    @DisplayName("Успешное изменение имени пользователя")
    public void changeNameSuccessUserTest() {
        ValidatableResponse createResponse = userClient.create(user);
        UserResponse userBeforeChange = userAssertions.createdSuccessfully(createResponse);
        accessToken = userBeforeChange.getAccessToken();
        userData = userBeforeChange.getUser();
        userData.setName(RandomStringUtils.randomAlphanumeric(10));
        ValidatableResponse createResponseChangeUser = userClient.changeUser(accessToken, userData);
        UserResponse userAfterChange = userAssertions.changeUserSuccess(createResponseChangeUser);

        assertEquals(
                userData.getName(),
                userAfterChange.getUser().getName(),
                "Новое имя не совпадает с ожидаемым значением!"
        );
    }

    @Test
    @DisplayName("Отсутствует токен. Ошибка при изменении email пользователя")
    public void changeEmailFailedTest() {
        ValidatableResponse createResponse = userClient.create(user);
        UserResponse userBeforeChange = userAssertions.createdSuccessfully(createResponse);
        accessToken = userBeforeChange.getAccessToken();
        userData = userBeforeChange.getUser();
        userData.setEmail(RandomStringUtils.randomAlphanumeric(5) + "@yandex.ru");
        ValidatableResponse createResponseChangeUser = userClient.changeUser(null, userData);
        userAssertions.changeUserFailed(createResponseChangeUser);
    }

    @Test
    @DisplayName("Отсутствует токен. Ошибка при изменении имени пользователя")
    public void changeNameFailedTest() {
        ValidatableResponse createResponse = userClient.create(user);
        UserResponse userBeforeChange = userAssertions.createdSuccessfully(createResponse);
        accessToken = userBeforeChange.getAccessToken();
        userData = userBeforeChange.getUser();
        userData.setName(RandomStringUtils.randomAlphanumeric(10));
        ValidatableResponse createResponseChangeUser = userClient.changeUser(null, userData);
        userAssertions.changeUserFailed(createResponseChangeUser);
    }
}
