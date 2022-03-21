package app.Services;
import app.Persistance.Entities.*;
import app.Persistance.Repositories.CartProductRepository;
import app.Persistance.Repositories.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartProductService cartProductService;
    private final CartPersService cartPersService;

    public CartService(CartRepository cartRepository,
                       CartProductService cartProductService,
                       CartPersService cartPersService) {
        this.cartRepository = cartRepository;
        this.cartProductService = cartProductService;
        this.cartPersService = cartPersService;
    }

    public Cart updateCart(Cart cart){
        double total = 0.0;
        List<CartProduct> cartProducts = cartProductService.findByCart(cart);
        List<CartPers> cartPersList = cartPersService.findByCart(cart);
        for(CartProduct cartProduct : cartProducts){
            cartProductService.updateCartProduct(cartProduct);
            total += cartProduct.getTotal();
        }
        for(CartPers cartPers : cartPersList){
            cartPersService.updateCartProduct(cartPers);
            total += cartPers.getTotal();
        }
        cart.setTotal(total);
        cartRepository.save(cart);
        return cart;
    }


}
