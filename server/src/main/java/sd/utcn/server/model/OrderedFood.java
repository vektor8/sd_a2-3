package sd.utcn.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderedFood {
    @Id
    @SequenceGenerator(
            name = "ordered_food_sequence",
            sequenceName = "ordered_food_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "ordered_food_sequence"
    )
    @Column(
            name = "id"
    )
    private Long id;

    @ManyToOne
    private Food food;

    private Integer quantity;

    @ManyToOne
    private Order order;

    public OrderedFood(Food food, Integer quantity, Order order) {
        this.food = food;
        this.quantity = quantity;
        this.order = order;
    }
}
