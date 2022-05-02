package sd.utcn.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.mindrot.jbcrypt.BCrypt;
import sd.utcn.server.repository.AdminRepository;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class Admin extends User{

    @OneToMany
    private List<Restaurant> restaurants;

    public Admin() {

    }

    public void addRestaurant(Restaurant r){
        restaurants.add(r);
    }
}
