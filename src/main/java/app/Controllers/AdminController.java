package app.Controllers;

import app.DTO.ProductDTO;
import app.Persistance.Entities.*;
import app.Services.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Controller
@Transactional
@RequestMapping("/admin")
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrdersService ordersService;
    private final OrderProductService orderProductService;

    public AdminController(CategoryService categoryService, ProductService productService, OrdersService ordersService,OrderProductService orderProductService) {
        this.ordersService = ordersService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderProductService = orderProductService;
    }
    @GetMapping
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/categories/{pageNo}")
    public String getCategory(Model model,@PathVariable(value = "pageNo") Integer pageNo) {
        int pageSize = 3;
        Page<Category> page = categoryService.findPaginated(pageNo, pageSize);
        List<Category> listCategories = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("categories", listCategories);
        return "categories";

    }

    @GetMapping("/categories")
    public String viewCategoriessPage(Model model){
        return getCategory(model,1);
    }

    @GetMapping("/categories/add")
    public String getCategoryAdd(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable UUID id, Model model) {
        Optional<Category> category = categoryService.updateCategory(id);
        model.addAttribute("category", category.get());
        categoryService.updateCategory(id);
        return "categoriesAdd";

    }
    @GetMapping("/products")
    public String viewProductsPage(Model model){
        return getProduct(1,"price", "asc",model);
    }

    @GetMapping("/products/{pageNo}")
    public String getProduct(@PathVariable(value = "pageNo") Integer pageNo,
                             @RequestParam(value = "sortField") String sortField,
                             @RequestParam(value = "sortDir") String sortDir,Model model) {
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
        model.addAttribute("product", listProducts);
        return "products";
    }

    @GetMapping("/products/add")
    public String getProductAdd(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO, @RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.updateCategory(productDTO.getCategoryId()).get());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        String imageUUID;
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";

    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable UUID id){
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/update/{id}")
    public String updateProduct(@PathVariable UUID id, Model model){
        Product product = productService.updateProduct(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("productDTO",productDTO);

        return "productsAdd";
    }


    @GetMapping("/orders/{pageNo}")
    public String getOrders(@PathVariable(value = "pageNo") Integer pageNo,
                            @RequestParam(value = "sortField") String sortField,
                            @RequestParam(value = "sortDir") String sortDir,Model model) {
        int pageSize = 3;
        Page<Orders> page = ordersService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Orders> listOrders = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("orders", listOrders);
        return "orders";
    }
    @GetMapping("/orders")
    public String viewOrdersPage(Model model){
        return getOrders(1,"orderDate", "asc",model);
    }

    @GetMapping("/getOrder/{id}")
    public String getOrder(@PathVariable UUID id,Model model) {
        Orders order = ordersService.getOrderById(id).get();
        User user = order.getUser();
        List<OrderProduct> orderProducts = order.getOrderProducts();
        List<OrderPers> orderPers = order.getOrderPers();
        model.addAttribute("order",order);
        model.addAttribute("user", user);
        model.addAttribute("orderProducts", orderProducts);
        model.addAttribute("orderPers", orderPers);
        return "showOrder";
    }



}
