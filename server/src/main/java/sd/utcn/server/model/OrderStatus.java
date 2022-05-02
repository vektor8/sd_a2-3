package sd.utcn.server.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING(0),
    ACCEPTED(1),
    IN_DELIVERY(2),
    DELIVERED(3),
    DECLINED(4);
    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }
}
