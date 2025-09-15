package praktikum.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
    private String success;
    private String name;
    private Order order;

}
