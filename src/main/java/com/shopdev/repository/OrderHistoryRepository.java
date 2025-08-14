package com.shopdev.repository;

import com.shopdev.model.OrderEntity;
import com.shopdev.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, String> {
    List<OrderHistory> findByOrderOrderByCreatedAtDesc(OrderEntity order);
}


