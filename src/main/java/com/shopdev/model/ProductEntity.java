package com.shopdev.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity extends AbstractEntity {
    @Column(name = "product_name", nullable = false, length = 150)
    String productName;

    @Column(name = "product_price", nullable = false, precision = 10, scale = 2)
    BigDecimal price;

    @Lob
    String description;

    @Column(name = "product_stock_keeping_unit", nullable = false)
    Long stockKeepingUnit;

    @Column(name = "product_image_url")
    String imageUrl;

    //    relationships
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    CategoryEntity category;
}
