package sd.utcn.server.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Customer extends User {
    @OneToMany
    List<Order> orders;
    public Customer(Long id, String email, String passwordHash, List<Order> orders){
        this.setEmail(email);
        this.setPasswordHash(passwordHash);
        this.setOrders(orders);
        this.setId(id);
    }
    public void addOrder(Order o){
        orders.add(o);
    }
}
