package app.Controllers;

import app.DTO.PersonalizedJewelryDTO;
import app.Persistance.Entities.*;
import app.Services.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class PersonalizedJewelryController {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartPersService service;

    public PersonalizedJewelryController(UserService userService, CartPersService service,ProductService productService,CategoryService categoryService) {
        this.userService = userService;
        this.service = service;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/personalized")
    public String personalized(Model model){
        return "personalized";
    }

    @GetMapping("/nameNecklace")
    public String getNameNecklace(Model model){
        model.addAttribute("personalJewerly", new PersonalizedJewelryDTO());
        return "nameNecklace";
    }

    @PostMapping("/addNameNecklece")
    public String addNameNecklace(@ModelAttribute("nameNecklace") PersonalizedJewelryDTO nameNecklace, Principal principal){
        User user = userService.findByUsername(principal.getName());
        PersonalizedJewelry personalizedJewelry = new PersonalizedJewelry();
        personalizedJewelry.setId(nameNecklace.getId());
        personalizedJewelry.setText(nameNecklace.getText());
        personalizedJewelry.setWithCristals(nameNecklace.getWithCristals());
        personalizedJewelry.setMaterial(nameNecklace.getMaterial());
        personalizedJewelry.setImageName("lancicSaImenom.jpg");
        Double price = 500.00;
        int numOfLetters = (nameNecklace.getText()).replace(" ","").length();
        if(nameNecklace.getWithCristals() == true) {
            price += 400.00;
        }
        if(nameNecklace.getMaterial().equals("zlato")) {
            price += numOfLetters*400.00;
        }
        else if(nameNecklace.getMaterial().equals("srebro")) {
            price += numOfLetters*80;
        }
        personalizedJewelry.setPrice(price);
        CartPers cartPers = service.addProduct(personalizedJewelry,user);
        return "redirect:/cart/getCart";
    }

    @GetMapping("/personalized/removeProduct")
    public String removeProduct(@RequestParam("id") UUID id) {
        service.removeCartProduct(service.findById(id).get());
        return "redirect:/cart/getCart";
    }

    @GetMapping("/engraving")
    public String getEngraving(@RequestParam(defaultValue = "1", required = false, value = "pageNo") Integer pageNo,
                               @RequestParam(defaultValue = "price", required = false, value = "sortField") String sortField,
                               @RequestParam(defaultValue = "asc", required = false, value = "sortDir") String sortDir, Model model){
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
        return "engraving";
    }

    @GetMapping("/engraving/category/{id}")
    public String shopByCategory(Model model, @PathVariable UUID id) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id));
        return "engraving";
    }

    @GetMapping("/personalized/engraving/{id}")
    public String getEngravingForm(Model model,@PathVariable UUID id){
        Product product = new Product();
        product = productService.getProductById(id).get();
        PersonalizedJewelryDTO personalizedJewelryDTO = new PersonalizedJewelryDTO();
        personalizedJewelryDTO.setProduct(product);
        model.addAttribute("product", product);
        model.addAttribute("personalJewerly", personalizedJewelryDTO);
        return "engravingAdd";
    }

    @PostMapping("/addEngraving")
    public String addEngraving(@ModelAttribute("engraving") PersonalizedJewelryDTO engraving,Principal principal){
        User user = userService.findByUsername(principal.getName());
        PersonalizedJewelry personalizedJewelry = new PersonalizedJewelry();
        personalizedJewelry.setId(engraving.getId());
        personalizedJewelry.setText(engraving.getText());
        personalizedJewelry.setWithCristals(engraving.getWithCristals());
        personalizedJewelry.setImageName(engraving.getProduct().getImageName());
        personalizedJewelry.setWithCristals(false);
        personalizedJewelry.setPrice(engraving.getProduct().getPrice() + (((engraving.getText()).replace(" ", "").length())*10));
        CartPers cartPers = service.addProduct(personalizedJewelry,user);
        return "redirect:/cart/getCart";
    }
}
