package sd.utcn.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.utcn.server.model.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
