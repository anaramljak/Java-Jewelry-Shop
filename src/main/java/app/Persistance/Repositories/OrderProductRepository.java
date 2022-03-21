package app.Persistance.Repositories;

import app.Persistance.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
    List<OrderProduct> findByOrders(Orders orders);
    public List<OrderProduct> findByProduct(Product product);
}
