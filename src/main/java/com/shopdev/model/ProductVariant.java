package com.shopdev.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_product_variant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    ProductEntity product;

    @Column(name = "sku", unique = true, length = 80)
    String sku;

    @Column(name = "variant_name", length = 160)
    String name; // e.g. "Red / L"

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "stock_quantity")
    Integer stockQuantity;
}


