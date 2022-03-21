package app.Persistance.Repositories;

import app.Persistance.Entities.Cart;
import app.Persistance.Entities.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartProductRepository extends JpaRepository<CartProduct, UUID>{
    public Optional<CartProduct> findById(UUID id);
    public List<CartProduct> findByCart(Cart cart);
}