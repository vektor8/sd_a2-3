package sd.utcn.server.model.state;

import sd.utcn.server.model.Order;

public abstract class State {
    protected Order order;

    public State(Order order) {
        this.order = order;
    }

    public abstract void advanceOrder();
    public abstract void declineOrder();
}
