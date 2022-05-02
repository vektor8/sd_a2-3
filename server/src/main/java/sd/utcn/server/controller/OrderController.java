package sd.utcn.server.controller;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sd.utcn.server.dto.NewOrderDto;
import sd.utcn.server.dto.OrderDto;
import sd.utcn.server.dto.OrderStatusDto;
import sd.utcn.server.service.OrderService;
import sd.utcn.server.viewmodels.NewOrderViewModel;

import java.io.IOException;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> placeOrder(Authentication authentication, @RequestBody NewOrderViewModel order) {
        try {
            log.info("Trying to place an order from customer {}", authentication.getName());
            var dto = new NewOrderDto(Long.parseLong(authentication.getName()), order.getOrderedFoods(),
                    order.getRestaurantId(), order.getAddress(), order.getOrderDetails());
            var res = orderService.addOrder(dto);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "{orderId}")
    public ResponseEntity<OrderDto> SetOrderStatus(@PathVariable("orderId") Long id, @RequestBody OrderStatusDto orderStatus) {
        try {
            log.info("Trying to change the order status to {}", orderStatus.getOrderStatus());
            var dto = orderService.changeOrderStatus(id, orderStatus.getOrderStatus());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public static void main(String[] args) throws IOException {
    }
}
