package sd.utcn.server.mapper;

import sd.utcn.server.dto.AdminRestaurantDto;
import sd.utcn.server.model.Restaurant;

public class AdminRestaurantMapper {
    public static AdminRestaurantDto ToDto(Restaurant restaurant) {
        var r = new AdminRestaurantDto();
        if (restaurant.getFoods() != null)
            r.setFoods(restaurant.getFoods().stream().map(FoodMapper::ToDto).toList());
        r.setId(restaurant.getId());
        r.setName(restaurant.getName());
        r.setLocation(restaurant.getLocation());
        if (restaurant.getOrders() != null)
            r.setOrders(restaurant.getOrders().stream().map(OrderMapper::ToDto).toList());
        return r;
    }
}
