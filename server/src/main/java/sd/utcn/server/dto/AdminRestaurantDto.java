package sd.utcn.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AdminRestaurantDto extends RestaurantDto {
    private List<OrderDto> orders;
}
