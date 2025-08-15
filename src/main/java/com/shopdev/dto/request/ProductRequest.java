package com.shopdev.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest implements Serializable {
    String product_name;
    BigDecimal product_price;

    Long category_id;
    String brand_id;
    List<String> tag_ids;
    List<String> image_urls;
    List<VariantItem> variants;

    Integer stockQuantity;
    String status; // IN_STOCK, OUT_OF_STOCK, LOW_STOCK

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class VariantItem implements Serializable {
        String sku;
        String name;
        BigDecimal price;
        Integer stockQuantity;
    }
}
