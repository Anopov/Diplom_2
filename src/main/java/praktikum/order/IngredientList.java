package praktikum.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientList {
    private boolean success;
    private List<Ingredient> data;

    public List<String> getIdIngredients() {
        List<String> list = new ArrayList<>();
        for (Ingredient i : data) {
            list.add(i.get_id());
        }
        return list;
    }
}
