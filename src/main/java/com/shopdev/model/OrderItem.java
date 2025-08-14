package com.shopdev.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_order_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    OrderEntity order;

    @Column(name = "product_name")
    String productName;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "price", precision = 18, scale = 2)
    BigDecimal price;
}


