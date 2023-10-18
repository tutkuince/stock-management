package io.ince.stockmanagement.productservice.controller;

import io.ince.stockmanagement.productservice.enums.Language;
import io.ince.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import io.ince.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import io.ince.stockmanagement.productservice.repository.entity.Product;
import io.ince.stockmanagement.productservice.request.ProductCreateRequest;
import io.ince.stockmanagement.productservice.response.FriendlyMessage;
import io.ince.stockmanagement.productservice.response.InternalAPIResponse;
import io.ince.stockmanagement.productservice.response.ProductResponse;
import io.ince.stockmanagement.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/{language}")
    public ResponseEntity<InternalAPIResponse<ProductResponse>> createProduct(
            @PathVariable Language language,
            @RequestBody ProductCreateRequest request) {
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), request);
        Product product = productService.createProduct(language, request);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productResponse);
        InternalAPIResponse<ProductResponse> response = convertProductResponseToInternalResponse(language, productResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private static InternalAPIResponse<ProductResponse> convertProductResponseToInternalResponse(Language language, ProductResponse productResponse) {
        return InternalAPIResponse.<ProductResponse>builder()
                .friendlyMessage(
                        FriendlyMessage.builder()
                                .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                                .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_CREATED)).build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    private static ProductResponse convertProductResponse(Product product) {
        return ProductResponse.builder()
                .productName(product.getProductName())
                .productId(product.getProductId())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .productCreatedDate(product.getProductCreatedDate().getLong(ChronoField.EPOCH_DAY))
                .productUpdatedDate(product.getProductUpdatedDate().getLong(ChronoField.EPOCH_DAY)).build();
    }
}
