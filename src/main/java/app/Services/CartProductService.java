package app.Services;

import app.Persistance.Entities.*;
import app.Persistance.Repositories.CartProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartProductService {
    private final CartProductRepository cartProductRepository;

    public CartProductService(CartProductRepository cartProductRepository) {
        this.cartProductRepository = cartProductRepository;
    }

    public List<CartProduct> findByCart(Cart cart){
        return  cartProductRepository.findByCart(cart);
    }
    public Optional<CartProduct> findById(UUID id){
        return  cartProductRepository.findById(id);
    }

   public CartProduct updateCartProduct(CartProduct cartProduct){
        double total = 0;
        total = (cartProduct.getProduct().getPrice()) * (cartProduct.getQty());
        cartProduct.setTotal(total);
        cartProductRepository.save(cartProduct);
        return cartProduct;
    }

    public CartProduct addProduct(Product product, User user){
        List<CartProduct> cartProducts = findByCart(user.getCart());
        for(CartProduct cartProduct : cartProducts){
            if(product.getId() == cartProduct.getProduct().getId()){
                cartProductRepository.save(cartProduct);
                return cartProduct;
            }
        }
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(user.getCart());
        cartProduct.setProduct(product);
        cartProduct.setQty(1);
        cartProduct.setTotal(product.getPrice());
        cartProduct = cartProductRepository.save(cartProduct);
        return cartProduct;
    }

    public void removeCartProduct(CartProduct cartProduct) {
        cartProductRepository.delete(cartProduct);
    }
}
