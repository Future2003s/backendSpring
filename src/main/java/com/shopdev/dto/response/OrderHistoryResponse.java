package com.shopdev.dto.response;

import com.shopdev.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderHistoryResponse implements Serializable {
    String id;
    OrderStatus oldStatus;
    OrderStatus newStatus;
    String changedBy;
    String note;
    LocalDateTime createdAt;
}


