package praktikum.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserListResponse {
    private boolean success;
    private List<OrderList> orders;
    private int total;
    private int totalToday;
}
