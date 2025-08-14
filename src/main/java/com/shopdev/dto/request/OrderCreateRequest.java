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
public class OrderCreateRequest implements Serializable {
    BigDecimal amount;
    String description;
    String paymentMethod; // cod | bank
    Customer customer;
    List<Item> items;

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Customer implements Serializable {
        String fullName;
        String phone;
        String email;
        String address;
        String note;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Item implements Serializable {
        String name;
        Integer quantity;
        BigDecimal price;
    }
}


