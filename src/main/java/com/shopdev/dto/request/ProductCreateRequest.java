package com.shopdev.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreateRequest implements Serializable {
    String productName;

    BigDecimal price;

    String description;

    Long stockKeepingUnit;
    String imageUrl;

    Long categoryId;
}
