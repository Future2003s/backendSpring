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
public class ProductUpdateRequest implements Serializable {
    // Match với frontend payload
    String name;                    // Thay vì product_name
    String description;             // Thêm description
    BigDecimal price;               // Thay vì product_price
    Integer stock;                  // Thay vì stockQuantity
    String sku;                     // Thêm sku
    Long categoryId;                // Thay vì category_id
    String brandId;                 // Thay vì brand_id
    List<String> images;            // Thay vì image_urls
    String status;                  // Giữ nguyên
    
    // Giữ lại các trường cũ để backward compatibility
    String product_name;
    BigDecimal product_price;
    Long category_id;
    String brand_id;
    List<String> tag_ids;
    List<String> image_urls;
    Integer stockQuantity;
}


