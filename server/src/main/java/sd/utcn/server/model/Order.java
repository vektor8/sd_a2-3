package sd.utcn.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sd.utcn.server.model.state.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Table
@Entity(name = "orders")
@Getter
@Setter
@SuperBuilder
public class Order {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "order_sequence"
    )
    @Column(
            name = "id"
    )
    private Long id;
    private OrderStatus orderStatus;

    @Transient
    private State state;

    public void advanceStatus() {
        state.advanceOrder();
    }

    public void declineOrder() {
        state.declineOrder();
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        updateState();
    }

    public Order() {
        orderStatus = OrderStatus.PENDING;
        state = new PendingState(this);
    }

    public void updateState() {
        state = switch (orderStatus) {
            case PENDING -> new PendingState(this);
            case ACCEPTED -> new AcceptedState(this);
            case DECLINED -> new DeclinedState(this);
            case IN_DELIVERY -> new InDeliveryState(this);
            default -> new DeliveredState(this);
        };
    }

    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderedFood> orderedFoods;

    @ManyToOne
    private Restaurant restaurant;

    public String address;
    public String orderDetails;
}
