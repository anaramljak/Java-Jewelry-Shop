package app.Controllers;
import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import app.Persistance.Entities.Product;
import app.Services.ProductService;

@RestController
public class ProductController {
	private ProductService service;

	@PostMapping (value = "/products")
	public Product addProduct(@RequestBody Product product) {
		return service.addProduct(product);
	}
	
	@GetMapping(value="/products")
	public List<Product> findAllProducts() {
		return service.getProducts();
	}
}
