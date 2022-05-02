package sd.utcn.server.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sd.utcn.server.dto.NewOrderDto;
import sd.utcn.server.dto.NewOrderedFoodDto;
import sd.utcn.server.dto.OrderDto;
import sd.utcn.server.mapper.OrderMapper;
import sd.utcn.server.model.Customer;
import sd.utcn.server.model.Food;
import sd.utcn.server.model.Order;
import sd.utcn.server.model.Restaurant;
import sd.utcn.server.repository.CustomerRepository;
import sd.utcn.server.repository.FoodRepository;
import sd.utcn.server.repository.OrderRepository;
import sd.utcn.server.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {
    @MockBean
    private final OrderRepository orderRepository;

    @MockBean
    private final RestaurantRepository restaurantRepository;

    @MockBean
    private final FoodRepository foodRepository;

    @MockBean
    private final CustomerRepository customerRepository;

    private final OrderService orderService;

    @Autowired
    public OrderServiceTests(OrderRepository orderRepository, RestaurantRepository restaurantRepository, FoodRepository foodRepository, CustomerRepository customerRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.customerRepository = customerRepository;
        this.orderService = orderService;
    }

    @Test
    public void addOrderTest() {
        var food = Food.builder().id(19L).price(21.0).build();
        var restaurant = Restaurant.builder().id(20L).orders(new ArrayList<>()).build();
        food.setRestaurant(restaurant);
        var customer = new Customer();
        customer.setId(30L);
        customer.setOrders(new ArrayList<>());
        var orderedFood = new NewOrderedFoodDto(19L, 1);
        var order = NewOrderDto.builder().orderedFoods(List.of(orderedFood))
                .customerId(30L).restaurantId(20L).build();
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        Mockito.when(restaurantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurant));
        Mockito.when(foodRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(food));
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(Order.builder()
                .id(50L)
                .restaurant(restaurant)
                .orderedFoods(new ArrayList<>()).build());
        assertDoesNotThrow(() -> orderService.addOrder(order));
    }

    @Test
    public void addOrderTestNoProducts() {
        var food = Food.builder().id(19L).price(21.0).build();
        var restaurant = Restaurant.builder().id(20L).orders(new ArrayList<>()).build();
        food.setRestaurant(restaurant);
        var customer = new Customer();
        customer.setId(30L);
        customer.setOrders(new ArrayList<>());
        var order = NewOrderDto.builder().orderedFoods(new ArrayList<>())
                .customerId(30L).restaurantId(20L).build();
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        Mockito.when(restaurantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurant));
        Mockito.when(foodRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(food));
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(Order.builder()
                .id(50L)
                .restaurant(restaurant)
                .orderedFoods(new ArrayList<>()).build());
        Exception exception = assertThrows(Exception.class, () -> orderService.addOrder(order));
    }
}
