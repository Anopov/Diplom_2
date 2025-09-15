import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.user.User;
import praktikum.user.UserAssertions;
import praktikum.user.UserClient;
import praktikum.user.UserGenerator;

public class CreateUserTest {
    private final UserGenerator generator = new UserGenerator();
    private final User user = generator.random();
    private final UserClient userClient = new UserClient();
    private final UserAssertions userAssertions = new UserAssertions();
    private String accessToken;

    @AfterEach
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse validatableResponse = userClient.delete(accessToken);
            userAssertions.deletedSuccessfully(validatableResponse);
        }
    }

    @Test
    @DisplayName("Успешное создание пользователя")
    public void createSuccessUserTest() {
        ValidatableResponse createResponse = userClient.create(user);
        accessToken = userAssertions.createdSuccessfully(createResponse).getAccessToken();
    }

    @Test
    @DisplayName("Ошибка создания пользователя. Пользователь уже зарегистрирован")
    public void createTheSameUserTest() {
        //Создание первого пользователя
        ValidatableResponse createResponse1 = userClient.create(user);
        userAssertions.createdSuccessfully(createResponse1);
        accessToken = userAssertions.createdSuccessfully(createResponse1).getAccessToken();//извлекаем токен для последющего удаления
        //Проверка повторного создания пользователя с теми же кредами
        ValidatableResponse createResponse2 = userClient.create(user);
        userAssertions.createdFaillyUserExist(createResponse2);
    }

    @Test
    @DisplayName("Ошибка создания пользователь. Email отсутствует")
    public void emptyEmail() {
        user.setEmail(null);
        ValidatableResponse createResponse = userClient.create(user);
        userAssertions.createdFaillyEmptyRequiredField(createResponse);
    }

    @Test
    @DisplayName("Ошибка создания пользователь. Password отсутствует")
    public void emptyPassword() {
        user.setPassword(null);
        ValidatableResponse createResponse = userClient.create(user);
        userAssertions.createdFaillyEmptyRequiredField(createResponse);
    }

    @Test
    @DisplayName("Ошибка создания пользователь. Name отсутствует")
    public void emptyName() {
        user.setName(null);
        ValidatableResponse createResponse = userClient.create(user);
        userAssertions.createdFaillyEmptyRequiredField(createResponse);
    }
}
