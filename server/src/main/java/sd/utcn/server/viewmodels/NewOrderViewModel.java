package sd.utcn.server.viewmodels;

import lombok.Getter;
import sd.utcn.server.dto.NewOrderedFoodDto;

import java.util.List;

@Getter
public class NewOrderViewModel {
    private List<NewOrderedFoodDto> orderedFoods;
    private Long restaurantId;
    public String address;
    public String orderDetails;
}
