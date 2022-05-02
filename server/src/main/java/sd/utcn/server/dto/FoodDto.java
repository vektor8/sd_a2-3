package sd.utcn.server.dto;

import lombok.Getter;
import lombok.Setter;
import sd.utcn.server.model.state.FoodCategory;

@Setter
@Getter
public class FoodDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private FoodCategory category;
}
