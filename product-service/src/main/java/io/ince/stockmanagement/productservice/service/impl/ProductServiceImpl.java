package io.ince.stockmanagement.productservice.service.impl;

import io.ince.stockmanagement.productservice.enums.Language;
import io.ince.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import io.ince.stockmanagement.productservice.exception.exceptions.ProductAlreadyDeletedException;
import io.ince.stockmanagement.productservice.exception.exceptions.ProductNotCreatedException;
import io.ince.stockmanagement.productservice.exception.exceptions.ProductNotFoundException;
import io.ince.stockmanagement.productservice.repository.ProductRepository;
import io.ince.stockmanagement.productservice.repository.entity.Product;
import io.ince.stockmanagement.productservice.request.ProductCreateRequest;
import io.ince.stockmanagement.productservice.request.ProductUpdatedRequest;
import io.ince.stockmanagement.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
                    .active(true)
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
        log.debug("[{}][getProduct] -> request: {}", this.getClass().getSimpleName(), productId);
        Product product = productRepository.findByProductIdAndActiveTrue(productId);
        if (Objects.isNull(product))
            throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND, "Product not found for ProductId: " + productId);
        log.debug("[{}][getProduct] -> request: {}", this.getClass().getSimpleName(), product);
        return product;
    }

    @Override
    public List<Product> getAllProducts(Language language) {
        log.debug("[{}][getAllProducts] -> request: {}", this.getClass().getSimpleName(), language);
        List<Product> productList = productRepository.getAllByActiveTrue();
        if (productList.isEmpty()) {
            throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND, "Product not found");
        }
        log.debug("[{}][getAllProducts] -> request: {}", this.getClass().getSimpleName(), productList);
        return productList;
    }

    @Override
    public Product updateProduct(Language language, ProductUpdatedRequest productUpdatedRequest) {
        log.debug("[{}][updateProduct] -> request: {}", this.getClass().getSimpleName(), productUpdatedRequest);
        Product inDB = getProduct(language, productUpdatedRequest.getProductId());
        inDB.setProductName(productUpdatedRequest.getProductName());
        inDB.setProductUpdatedDate(LocalDateTime.now());
        inDB.setQuantity(productUpdatedRequest.getQuantity());
        inDB.setPrice(productUpdatedRequest.getPrice());
        Product productResponse = productRepository.save(inDB);
        log.debug("[{}][updateProduct] -> request: {}", this.getClass().getSimpleName(), productResponse);
        return productResponse;
    }

    @Override
    public Product deleteProduct(Language language, Long productId) {
        log.debug("[{}][updateProduct] -> request: {}", this.getClass().getSimpleName(), productId);
        Product product;
        try {
            product = getProduct(language, productId);
            product.setActive(false);
            product.setProductUpdatedDate(LocalDateTime.now());
            Product productResponse = productRepository.save(product);
            log.debug("[{}][updateProduct] -> request: {}", this.getClass().getSimpleName(), productId);
            return product;
        } catch (Exception e) {
            throw new ProductAlreadyDeletedException(language, FriendlyMessageCodes.PRODUCT_ALREADY_DELETED, "Product already deleted ProductId: " + productId);
        }
    }
}
