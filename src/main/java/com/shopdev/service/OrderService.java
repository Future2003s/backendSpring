package com.shopdev.service;

import com.shopdev.dto.request.OrderCreateRequest;
import com.shopdev.dto.response.OrderResponse;
import com.shopdev.dto.response.OrderHistoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderCreateRequest request);
    Page<OrderResponse> list(int page, int size);
    OrderResponse updateStatus(String orderId, String status);
    List<OrderHistoryResponse> history(String orderId);
}


