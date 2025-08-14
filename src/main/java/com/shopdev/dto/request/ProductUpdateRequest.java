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
    String product_name;
    BigDecimal product_price;

    Long category_id;
    String brand_id;
    List<String> tag_ids;
    List<String> image_urls;
}


