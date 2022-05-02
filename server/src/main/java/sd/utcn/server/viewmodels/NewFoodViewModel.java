package sd.utcn.server.viewmodels;

import lombok.Getter;
import sd.utcn.server.model.state.FoodCategory;

@Getter
public class NewFoodViewModel {
    private String name;
    private String description;
    private Double price;
    private Long restaurantId;
    private FoodCategory category;
}
