package sd.utcn.server.mapper;

import sd.utcn.server.dto.NewOrderDto;
import sd.utcn.server.dto.OrderDto;
import sd.utcn.server.model.Order;
import sd.utcn.server.model.OrderStatus;

public class OrderMapper {

    public static Order ToEntity(NewOrderDto newOrder) {
        var o = new Order();
        o.setOrderStatus(OrderStatus.PENDING);
        o.setOrderedFoods(newOrder.getOrderedFoods().stream()
                .map(OrderedFoodMapper::ToEntity).toList());
        return o;
    }

    public static OrderDto ToDto(Order order){
        var o = new OrderDto();
        o.setId(order.getId());
        o.setOrderStatus(order.getOrderStatus());
        o.setOrderedFoods(order.getOrderedFoods()
                .stream().map(OrderedFoodMapper::ToDto).toList());
        o.setOrderDetails(order.getOrderDetails());
        o.setAddress(order.getAddress());
        return o;
    }
}
