package com.shopdev.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.shopdev.enums.ProductStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity extends AbstractEntity {
    @Column(name = "product_name")
    String product_name;

    @Column(name = "product_price")
    BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    BrandEntity brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductImage> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductVariant> variants;

    @ManyToMany
    @JoinTable(
            name = "tbl_product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    Set<TagEntity> tags;

    @Column(name = "stock_quantity")
    Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32)
    ProductStatus status;
}
