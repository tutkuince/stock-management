package io.ince.stockmanagement.productservice.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", schema = "stock_management")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Builder.Default
    @Column(name = "product_updated_date")
    private LocalDateTime productUpdatedDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "product_created_date")
    private LocalDateTime productCreatedDate = LocalDateTime.now();

    @Column(name = "is_active")
    private boolean active;
}
