package app.Controllers;

import app.DTO.PersonalizedJewelryDTO;
import app.Persistance.Entities.*;
import app.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CheckoutController {

    private final UserService userService;
    private final CartProductService cartProductService;
    private final AddressService addressService;
    private final CartService cartService;
    private final CartPersService cartPersService;
    private final OrdersService ordersService;
    private final OrderProductService orderProductService;
    private final OrderPersService orderPersService;

    public CheckoutController(UserService userService, CartProductService cartProductService,
                              AddressService addressService, CartService cartService,
                              OrdersService ordersService, OrderProductService orderProductService,
                              CartPersService cartPersService,
                              OrderPersService orderPersService) {
        this.userService = userService;
        this.cartProductService = cartProductService;
        this.addressService = addressService;
        this.cartService = cartService;
        this.ordersService = ordersService;
        this.orderProductService = orderProductService;
        this.cartPersService = cartPersService;
        this.orderPersService = orderPersService;
    }

    @GetMapping("/checkout")
    public String checkout(
            @RequestParam(value="missingRequiredField", required = false) boolean missingRequiredField,
            Model model, Principal principal
    ) {
        User user = userService.findByUsername(principal.getName());
        if(user.getAddress() == null){
            return "redirect:/addresses/new";
        };
        List<CartProduct> cartProducts= cartProductService.findByCart(user.getCart());
        List<CartPers> cartPers = cartPersService.findByCart(user.getCart());
        if (cartProducts.size() == 0 && cartPers.size() == 0) {
            model.addAttribute("emptyCart", true);
            return "redirect:/cart";
        }
        Cart cart = user.getCart();
        Double prePrice = cart.getTotal();
        cart.setTotal(cart.getTotal() + 30);
        model.addAttribute("user", user);
        model.addAttribute("cartProduct", cartProducts);
        model.addAttribute("cartPers", cartPers);
        model.addAttribute("prePrice", prePrice);
        model.addAttribute("cart", cart);
        model.addAttribute("address", addressService.getAddress(user));
        return "checkout";
    }
  
    @PostMapping("/checkout/addOrder")
    public String addOrder(Principal principal){
        User user = userService.findByUsername(principal.getName());
        Orders order = new Orders();
        order.setUser(user);
        order.setTotal(user.getCart().getTotal() + 30);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MMM/yyyy");
        String dateOnly = dateFormat.format(currentDate);
        order.setOrderDate(currentDate);

        List<CartProduct> cartProducts = cartProductService.findByCart(user.getCart());
        List<CartPers> cartPersList = cartPersService.findByCart(user.getCart());
        List<OrderProduct> orderProducts = new ArrayList<>();
        List<OrderPers> orderPersList = new ArrayList<>();
        for(CartProduct cartProduct : cartProducts) {;
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(cartProduct.getProduct());
            orderProduct.setQty(cartProduct.getQty());
            orderProduct.setOrders(order);
            orderProduct = orderProductService.save(orderProduct);
            orderProducts.add(orderProduct);
            cartProductService.removeCartProduct(cartProduct);
        }
        for(CartPers cartPers : cartPersList) {;
            OrderPers orderPers = new OrderPers();
            orderPers.setPersonalizedJewelry(cartPers.getPersonalizedJewelry());
            orderPers.setQty(cartPers.getQty());
            orderPers.setOrders(order);
            orderPers = orderPersService.save(orderPers);
            orderPersList.add(orderPers);
            cartPersService.removeCartProduct(cartPers);
        }
        order.setOrderProducts(orderProducts);
        order.setOrderPers(orderPersList);
        ordersService.save(order);
        return "redirect:/cart/getCart";
    }
}
