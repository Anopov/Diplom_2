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
public class Order {
    private List<IngredientList> ingredients;
    private String _id;
    private Owner owner;
    private String status;
    private String createdAt;
    private String updatedAt;
    private int number;
    private int price;
    private String name;
}
