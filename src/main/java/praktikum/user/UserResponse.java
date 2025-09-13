package praktikum.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private boolean success;
    private Credentials credentials;
    private User user;
    private String accessToken;
    private String refreshToken;
}