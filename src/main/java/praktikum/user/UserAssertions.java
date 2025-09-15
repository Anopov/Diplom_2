package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.is;

public class UserAssertions {

    private UserResponse userResponse;

    @Step("Пользователь успешно создан")
    public UserResponse createdSuccessfully(ValidatableResponse validatableResponse) {
        validatableResponse.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
        ;
        // Извлекаем оригинальный объект Response
        Response response = validatableResponse.extract().response();

        // Преобразуем тело ответа в объект UserResponse
        return userResponse = response.as(UserResponse.class);
    }

    @Step("Пользователь успешно авторизовался")
    public void loginSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
        ;
    }

    @Step("Ошибка авторизации пользователя")
    public void invalidCredentials(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"))
        ;
    }

    @Step("Пользователь успешно удален")
    public void deletedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_ACCEPTED)
                .body("success", is(true))
        ;
    }

    @Step("Ошибка создания пользователя, такой пользователь уже существует")
    public void createdFaillyUserExist(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("User already exists"))
        ;
    }

    @Step("Ошибка создания пользователя, отсутствует обязательное поле")
    public void createdFaillyEmptyRequiredField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"))
        ;
    }


    @Step("Данные пользователя успешно изменены")
    public UserResponse changeUserSuccess(ValidatableResponse validatableResponse) {
        validatableResponse.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
        ;

        // Извлекаем оригинальный объект Response
        Response response = validatableResponse.extract().response();

        // Преобразуем тело ответа в объект UserResponse
        return userResponse = response.as(UserResponse.class);
    }

    @Step("Ошибка при изменении данных пользователя")
    public void changeUserFailed(ValidatableResponse validatableResponse) {
        validatableResponse.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"))
        ;
    }
}
