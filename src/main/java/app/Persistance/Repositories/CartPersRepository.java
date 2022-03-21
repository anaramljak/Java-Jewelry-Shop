package app.Persistance.Repositories;

import app.Persistance.Entities.Cart;
import app.Persistance.Entities.CartPers;
import app.Persistance.Entities.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartPersRepository extends JpaRepository<CartPers, UUID> {
    List<CartPers> findByCart(Cart cart);
}
