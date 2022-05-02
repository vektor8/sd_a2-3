package sd.utcn.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;
@Table
@Entity
@Getter
@Setter
@SuperBuilder
public class Restaurant {

    @Id
    @SequenceGenerator(
            name = "restaurant_sequence",
            sequenceName = "restaurant_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "restaurant_sequence"
    )
    @Column(
            name = "id"
    )
    private Long id;

    private String name;
    private String location;

    @ManyToOne
    private Admin admin;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Food> foods;

    public Restaurant() {

    }

    public void addOrder(Order o){
        orders.add(o);
    }
    @OneToMany
    private List<Order> orders;

    public void addFood(Food food){
        foods.add(food);
    }
}
