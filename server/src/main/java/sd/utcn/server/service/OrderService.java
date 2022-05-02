package sd.utcn.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.utcn.server.dto.NewOrderDto;
import sd.utcn.server.dto.OrderDto;
import sd.utcn.server.mapper.OrderMapper;
import sd.utcn.server.model.Order;
import sd.utcn.server.model.OrderStatus;
import sd.utcn.server.model.OrderedFood;
import sd.utcn.server.repository.CustomerRepository;
import sd.utcn.server.repository.FoodRepository;
import sd.utcn.server.repository.OrderRepository;
import sd.utcn.server.repository.RestaurantRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final CustomerRepository customerRepository;
    private final EmailService emailService;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository, FoodRepository foodRepository, CustomerRepository customerRepository, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.customerRepository = customerRepository;
        this.emailService = emailService;
    }

    /**
     * Tries to add new order if the input data is valid
     *
     * @param newOrder {
     *                 private Long customerId;
     *                 private List<NewOrderedFoodDto> orderedFoods;
     *                 private Long restaurantId;
     *                 }
     * @return
     * @throws Exception
     */
    @Transactional
    public OrderDto addOrder(NewOrderDto newOrder) throws Exception {
        if (newOrder.getOrderedFoods().isEmpty()) {
            log.error("No ordered foods");
            throw new Exception("No ordered foods");
        }

        var customer = customerRepository.findById(newOrder.getCustomerId());
        if (customer.isEmpty()) {
            log.error("Customer doesn't exist");
            throw new Exception("Customer doesn't exist");
        }

        var restaurant = restaurantRepository.findById(newOrder.getRestaurantId());
        if (restaurant.isEmpty()) {
            log.error("Nonexistent restaurant");
            throw new Exception("Restaurant doesn't exist");
        }
        var order = new Order();
        order.setCustomer(customer.get());
        order.setRestaurant(restaurant.get());
        List<OrderedFood> foods = new ArrayList<>();
        for (var f : newOrder.getOrderedFoods()) {
            var food = foodRepository.findById(f.getFoodId());
            if (food.isEmpty()) {
                log.error("no food found with id {}", f.getFoodId());
                throw new Exception("invalid food id in order");
            }
            if (!Objects.equals(food.get().getRestaurant().getId(), restaurant.get().getId())) {
                log.error("food {} is not from restaurant {}", food.get().getId(), restaurant.get().getId());
                throw new Exception("Food is not from the right restaurant");
            }
            foods.add(new OrderedFood(food.get(), f.getQuantity(), order));
        }
        customer.get().addOrder(order);
        restaurant.get().addOrder(order);
        order.setOrderedFoods(foods);
        order.setAddress(newOrder.getAddress());
        order.setOrderDetails(newOrder.getOrderDetails());
        var added = orderRepository.save(order);
        var dto = OrderMapper.ToDto(added);
        emailService.sendEmailToAdmin(dto, restaurant.get().getAdmin().getEmail());
        return dto;
    }

    /**
     * Tries to change order status but check if it respects the established pipeline
     *
     * @param id
     * @param orderStatus
     * @return
     * @throws Exception
     */
    @Transactional
    public OrderDto changeOrderStatus(Long id, OrderStatus orderStatus) throws Exception {
        var o = orderRepository.findById(id);
        if (o.isEmpty()) {
            log.error("Nonexistent order");
            throw new Exception("Nonexistent order");
        }
        o.get().updateState();

        if (orderStatus.getValue() - o.get().getOrderStatus().getValue() == 1)
            o.get().advanceStatus();
        if (orderStatus.getValue() - o.get().getOrderStatus().getValue() == 4)
            o.get().declineOrder();
        return OrderMapper.ToDto(o.get());
    }
}
