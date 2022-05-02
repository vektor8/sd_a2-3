package sd.utcn.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sd.utcn.server.model.state.FoodCategory;

@Builder
@Getter
@AllArgsConstructor
public class NewFoodDto {
    private String name;
    private String description;
    private Double price;
    private Long restaurantId;
    private FoodCategory category;
    private Long adminId;
}
