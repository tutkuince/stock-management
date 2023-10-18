package io.ince.stockmanagement.productservice.repository;

import io.ince.stockmanagement.productservice.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByProductIdAndActiveTrue(Long productId);

    List<Product> getAllByActiveTrue();
}
