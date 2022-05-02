package sd.utcn.server.model.state;

import sd.utcn.server.model.Order;
import sd.utcn.server.model.OrderStatus;

public class AcceptedState extends State{
    public AcceptedState(Order order) {
        super(order);
    }

    @Override
    public void advanceOrder() {
        this.order.setOrderStatus(OrderStatus.IN_DELIVERY);
    }

    @Override
    public void declineOrder() {

    }
}
