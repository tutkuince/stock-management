package io.ince.stockmanagement.productservice.service;

import io.ince.stockmanagement.productservice.enums.Language;
import io.ince.stockmanagement.productservice.repository.entity.Product;
import io.ince.stockmanagement.productservice.request.ProductCreateRequest;
import io.ince.stockmanagement.productservice.request.ProductUpdatedRequest;

import java.util.List;

public interface ProductService {
    Product createProduct(Language language, ProductCreateRequest productCreateRequest);

    Product getProduct(Language language, Long productId);

    List<Product> getAllProducts(Language language);

    Product updateProduct(Language language, ProductUpdatedRequest productUpdatedRequest);

    Product deleteProduct(Language language, Long productId);
}
