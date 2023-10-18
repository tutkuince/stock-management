package io.ince.stockmanagement.productservice.controller;

import io.ince.stockmanagement.productservice.enums.Language;
import io.ince.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import io.ince.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import io.ince.stockmanagement.productservice.repository.entity.Product;
import io.ince.stockmanagement.productservice.request.ProductCreateRequest;
import io.ince.stockmanagement.productservice.request.ProductUpdatedRequest;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{productId}/{language}")
    public ResponseEntity<InternalAPIResponse<ProductResponse>> getProduct(@PathVariable Long productId, @PathVariable Language language) {
        log.debug("[{}][getProduct] -> request: {}", this.getClass().getSimpleName(), productId);
        Product product = productService.getProduct(language, productId);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productResponse);
        InternalAPIResponse<ProductResponse> response = convertProductResponseToInternalResponse(language, productResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{productId}/{language}")
    public ResponseEntity<InternalAPIResponse<ProductResponse>> updateProduct(@PathVariable Long productId, @PathVariable Language language, @RequestBody ProductUpdatedRequest productUpdatedRequest) {
        log.debug("[{}][updateProduct] -> request: {}", this.getClass().getSimpleName(), productId);
        Product product = productService.updateProduct(language, productUpdatedRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][updateProduct] -> request: {}", this.getClass().getSimpleName(), productResponse);
        InternalAPIResponse<ProductResponse> response = convertProductResponseToInternalResponse(language, productResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{language}")
    public ResponseEntity<InternalAPIResponse<List<ProductResponse>>> getProductList(@PathVariable Language language) {
        log.debug("[{}][getProduct] -> request: {}", this.getClass().getSimpleName(), language);
        List<Product> productList = productService.getAllProducts(language);
        List<ProductResponse> productResponseList = convertProductResponseList(productList);
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productResponseList);
        InternalAPIResponse<List<ProductResponse>> response = InternalAPIResponse.<List<ProductResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponseList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{productId}/{language}")
    public ResponseEntity<InternalAPIResponse<ProductResponse>> deleteProduct(@PathVariable Long productId, @PathVariable Language language) {
        log.debug("[{}][deleteProduct] -> request: {}", this.getClass().getSimpleName(), productId);
        Product product = productService.deleteProduct(language, productId);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][deleteProduct] -> request: {}", this.getClass().getSimpleName(), productResponse);
        InternalAPIResponse<ProductResponse> response = convertProductResponseToInternalResponse(language, productResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private List<ProductResponse> convertProductResponseList(List<Product> productList) {
        return productList.stream()
                .map(arg -> ProductResponse.builder()
                        .productId(arg.getProductId())
                        .productName(arg.getProductName())
                        .quantity(arg.getQuantity())
                        .price(arg.getPrice())
                        .productCreatedDate(arg.getProductCreatedDate().getLong(ChronoField.EPOCH_DAY))
                        .productUpdatedDate(arg.getProductCreatedDate().getLong(ChronoField.EPOCH_DAY))
                        .build()

                ).collect(Collectors.toList());
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
