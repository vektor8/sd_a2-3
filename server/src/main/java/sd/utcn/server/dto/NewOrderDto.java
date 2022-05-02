package sd.utcn.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class NewOrderDto {
    private Long customerId;
    private List<NewOrderedFoodDto> orderedFoods;
    private Long restaurantId;
    public String address;
    public String orderDetails;
}
