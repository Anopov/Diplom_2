import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.user.*;

public class LoginUserTest {
    private final UserGenerator generator = new UserGenerator();
    private final User user = generator.random();
    private final UserClient userClient = new UserClient();
    private final UserAssertions userAssertions = new UserAssertions();
    private String accessToken;
    private Credentials credentials;

    @BeforeEach
    public void createSuccessUserTest() {
        ValidatableResponse createResponse = userClient.create(user);
        credentials = Credentials.from(user);
        accessToken = userAssertions.createdSuccessfully(createResponse).getAccessToken();
    }

    @AfterEach
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse validatableResponse = userClient.delete(accessToken);
            userAssertions.deletedSuccessfully(validatableResponse);
        }
    }

    @Test
    @DisplayName("Успешная авторизация пользователя")
    public void loginSuccessUserTest() {
        ValidatableResponse createResponse = userClient.login(credentials);
        userAssertions.loginSuccessfully(createResponse);
    }

    @Test
    @DisplayName("Ошибка авторизации. Некорректный email")
    public void loginInvalidEmailTest() {
        credentials.setEmail(RandomStringUtils.randomAlphanumeric(5)+"@yandex.ru");
        ValidatableResponse createResponse = userClient.login(credentials);
        userAssertions.invalidCredentials(createResponse);
    }

    @Test
    @DisplayName("Ошибка авторизации. Некорректный пароль")
    public void loginInvalidPasswordTest() {
        credentials.setEmail(RandomStringUtils.randomAlphanumeric(5));
        ValidatableResponse createResponse = userClient.login(credentials);
        userAssertions.invalidCredentials(createResponse);
    }

}
