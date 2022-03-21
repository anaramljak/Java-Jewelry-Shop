package app.Persistance.Repositories;

import app.Persistance.Entities.OrderPers;
import app.Persistance.Entities.OrderProduct;
import app.Persistance.Entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderPersRepository extends JpaRepository<OrderPers, UUID> {
    List<OrderPers> findByOrders(Orders orders);

}
