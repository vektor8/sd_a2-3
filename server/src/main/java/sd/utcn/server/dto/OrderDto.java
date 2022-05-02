package sd.utcn.server.dto;

import lombok.Getter;
import lombok.Setter;
import sd.utcn.server.model.OrderStatus;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private OrderStatus orderStatus;
    private List<OrderedFoodDto> orderedFoods;
    private String address;
    private String orderDetails;
}
