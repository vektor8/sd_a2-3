package sd.utcn.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.utcn.server.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
