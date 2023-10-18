package io.ince.stockmanagement.productservice.service.impl;

import io.ince.stockmanagement.productservice.enums.Language;
import io.ince.stockmanagement.productservice.exception.enums.FriendlyMessageCode;
import io.ince.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import io.ince.stockmanagement.productservice.exception.exceptions.ProductNotCreatedException;
import io.ince.stockmanagement.productservice.repository.ProductRepository;
import io.ince.stockmanagement.productservice.repository.entity.Product;
import io.ince.stockmanagement.productservice.request.ProductCreateRequest;
import io.ince.stockmanagement.productservice.request.ProductUpdatedRequest;
import io.ince.stockmanagement.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product createProduct(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productCreateRequest);
        try {
            Product product = Product.builder()
                    .productName(productCreateRequest.getProductName())
                    .isActive(true)
                    .price(productCreateRequest.getPrice())
                    .quantity(productCreateRequest.getQuantity())
                    .build();

            Product productResponse = productRepository.save(product);
            log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productResponse);
            return productResponse;
        } catch (Exception e) {
            throw new ProductNotCreatedException(language, FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION, "ProductCreateRequest: " + productCreateRequest.toString());
        }
    }

    @Override
    public Product getProduct(Language language, Long productId) {
        return null;
    }

    @Override
    public List<Product> getAllProducts(Language language) {
        return null;
    }

    @Override
    public Product updateProduct(Language language, ProductUpdatedRequest productUpdatedRequest) {
        return null;
    }

    @Override
    public Product deleteProduct(Language language, Long productId) {
        return null;
    }
}
