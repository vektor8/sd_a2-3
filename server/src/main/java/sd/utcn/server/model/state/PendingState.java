package sd.utcn.server.model.state;

import sd.utcn.server.model.Order;
import sd.utcn.server.model.OrderStatus;

public class PendingState extends State{
    public PendingState(Order order) {
        super(order);
    }

    @Override
    public void advanceOrder() {
        this.order.setOrderStatus(OrderStatus.ACCEPTED);
    }

    @Override
    public void declineOrder() {
        this.order.setOrderStatus(OrderStatus.DECLINED);
    }
}
