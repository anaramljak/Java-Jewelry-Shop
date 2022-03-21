package app.Services;

import app.Persistance.Entities.Cart;
import app.Persistance.Entities.CartPers;
import app.Persistance.Entities.PersonalizedJewelry;
import app.Persistance.Entities.User;
import app.Persistance.Repositories.CartPersRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartPersService {
    private final CartPersRepository repository;

    public CartPersService(CartPersRepository repository) {
        this.repository= repository;
    }
    public Optional<CartPers> findById(UUID id){
        return  repository.findById(id);
    }

    public List<CartPers> findByCart(Cart cart){
        return  repository.findByCart(cart);
    }

    public CartPers addProduct(PersonalizedJewelry product, User user){
        CartPers cartPers = new CartPers();
        cartPers.setCart(user.getCart());
        cartPers.setPersonalizedJewelry(product);
        cartPers.setQty(1);
        cartPers.setImageName(product.getImageName());
        cartPers.setTotal(product.getPrice());
        cartPers = repository.save(cartPers);
        return cartPers;
    }

    public CartPers updateCartProduct(CartPers cartPers){
        double total = (cartPers.getPersonalizedJewelry().getPrice())*(cartPers.getQty());
        cartPers.setTotal(total);
        repository.save(cartPers);
        return cartPers;
    }
    public void removeCartProduct(CartPers cartPers) {
        repository.delete(cartPers);
    }
}
