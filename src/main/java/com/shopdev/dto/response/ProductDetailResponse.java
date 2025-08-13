package com.shopdev.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {
    String id;
    String name;
    BigDecimal price;
    String brandName;
    String categoryName;
    List<String> imageUrls;
    List<Variant> variants;
    List<String> tags;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Variant {
        String id;
        String sku;
        String name;
        BigDecimal price;
        Integer stockQuantity;
    }
}


