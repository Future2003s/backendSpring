package com.shopdev.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductListItemResponse {
    String id;
    String name;
    BigDecimal price;
    String brandName;
    String categoryName;
    List<String> imageUrls;
    Integer stockQuantity;
    String status;
    
    // Additional fields for admin
    String sku;
    String description;
    String brandId;
    Long categoryId;
}


