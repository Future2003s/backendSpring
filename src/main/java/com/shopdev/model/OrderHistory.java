package com.shopdev.model;

import com.shopdev.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_order_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderHistory extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    OrderEntity order;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status")
    OrderStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    OrderStatus newStatus;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "note", length = 1000)
    String note;
}


