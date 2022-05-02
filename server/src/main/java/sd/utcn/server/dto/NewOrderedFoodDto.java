package sd.utcn.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewOrderedFoodDto {
    private Long foodId;
    private Integer quantity;
}
