package app.Controllers;

import app.Persistance.Entities.*;
import app.Persistance.Repositories.ProductRepository;
import app.Services.*;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Controller
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ProductRepository repository;
    private final OrderProductService orderProductService;
    private final OrderPersService orderPersService;
    private final OrdersService ordersService;
    private final UserService userService;

    public HomeController(CategoryService categoryService, ProductService productService,
                          ProductRepository repository, OrdersService ordersService,
                          UserService userService, OrderProductService orderProductService,
                          OrderPersService orderPersService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.repository = repository;
        this.ordersService = ordersService;
        this.userService = userService;
        this.orderProductService = orderProductService;
        this.orderPersService = orderPersService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        return "index";
    }



    @GetMapping("/shop")
    public String shop(@RequestParam(defaultValue = "1", required = false, value = "pageNo") Integer pageNo,
                       @RequestParam(defaultValue = "price", required = false, value = "sortField") String sortField,
                       @RequestParam(defaultValue = "asc", required = false, value = "sortDir") String sortDir, Model model) {
        int pageSize = 3;
        Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Product> listProducts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", listProducts);
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable UUID id) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id));
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable UUID id) {
        model.addAttribute("product", productService.getProductById(id).get());
        return "viewProduct";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "keyword") String keyword) {
        List<Product> products = productService.search(keyword);
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", products);
        return "shop";
    }
    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        int pageSize = 3;
        List<Orders> ordersList = user.getOrders();
        model.addAttribute("orders", ordersList);
        return "myOrders";
    }

    @GetMapping("/getOrder/{id}")
    public String getOrder(@PathVariable UUID id,Model model) {
        Orders order = ordersService.getOrderById(id).get();
        List<OrderProduct> orderProducts = order.getOrderProducts();
        List<OrderPers> orderPers = order.getOrderPers();
        model.addAttribute("order",order);
        model.addAttribute("orderProducts", orderProducts);
        model.addAttribute("orderPers", orderPers);
        return "showMyOrders";
    }

}
