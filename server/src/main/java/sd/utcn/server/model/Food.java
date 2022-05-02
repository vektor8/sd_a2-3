package sd.utcn.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sd.utcn.server.model.state.FoodCategory;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Food {

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
    private String description;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant Restaurant;

    private FoodCategory category;

    public Food() {

    }
}
