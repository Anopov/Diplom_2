package praktikum.user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public User generic() {
        return new User("Jack", "P@ssw0rd123", "Sparrow");
    }

    public User random() {
        return new User(RandomStringUtils.randomAlphanumeric(5)+"@yandex.ru", RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10));
    }
}
