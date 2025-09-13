package praktikum.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    private String email;
    private String password;

    public static Credentials from(User user) {
        return new Credentials(user.getEmail(), user.getPassword());
    }
}
