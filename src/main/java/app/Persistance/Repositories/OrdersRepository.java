package app.Persistance.Repositories;

import app.Persistance.Entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Orders, UUID>{

}
