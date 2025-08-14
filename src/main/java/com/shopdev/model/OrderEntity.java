package com.shopdev.model;

import com.shopdev.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tbl_order")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity extends AbstractEntity {
    @Column(name = "customer_full_name")
    String customerFullName;

    @Column(name = "customer_phone")
    String customerPhone;

    @Column(name = "customer_email")
    String customerEmail;

    @Column(name = "customer_address", length = 1000)
    String customerAddress;

    @Column(name = "customer_note", length = 2000)
    String customerNote;

    @Column(name = "amount", precision = 18, scale = 2)
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> items;
}


