package com.shopdev.dto.response;

import com.shopdev.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse implements Serializable {
    String id;
    String customerFullName;
    String customerPhone;
    String customerEmail;
    String customerAddress;
    String customerNote;
    BigDecimal amount;
    OrderStatus status;
    LocalDateTime createdAt;
    List<Item> items;

    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Item implements Serializable {
        String id;
        String productName;
        Integer quantity;
        BigDecimal price;
    }
}


