package app.Services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import app.Persistance.Entities.Product;
import app.Persistance.Repositories.ProductRepository;


@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product addProduct(Product product) {

        return repository.save(product);
    }

    public List<Product> getProducts() {

        return repository.findAll();
    }

    public void deleteProduct(UUID id) {
        repository.deleteById(id);
    }

    public Optional<Product> updateProduct(UUID id) {
        return repository.findById(id);
    }

    public List<Product> getAllProductsByCategoryId(UUID id) {
        return repository.findAllByCategoryId(id);
    }

    public Optional<Product> getProductById(UUID id) {
        return repository.findById(id);
    }

    public List<Product> search(String keyword) {
        List<Product> products = repository.findAll();
        return products == null ? new ArrayList<>() : products.stream().filter(product -> product.getName().contains(keyword)).collect(Collectors.toList());

    }

    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return repository.findAll(pageable);

    }




}