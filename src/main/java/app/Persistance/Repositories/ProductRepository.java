package app.Persistance.Repositories;

import java.util.List;
import java.util.Stack;
import java.util.UUID;

import app.Persistance.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByCategoryId(UUID id);
    Page<Product> findAll(Pageable pageable);



}