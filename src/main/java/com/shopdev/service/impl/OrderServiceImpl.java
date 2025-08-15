package com.shopdev.service.impl;

import com.shopdev.dto.request.OrderCreateRequest;
import com.shopdev.dto.response.OrderResponse;
import com.shopdev.dto.response.OrderHistoryResponse;
import com.shopdev.enums.OrderStatus;
import com.shopdev.model.OrderEntity;
import com.shopdev.model.OrderItem;
import com.shopdev.model.OrderHistory;
import com.shopdev.repository.OrderRepository;
import com.shopdev.repository.OrderHistoryRepository;
import com.shopdev.service.OrderService;
import org.springframework.context.ApplicationEventPublisher;
import com.shopdev.events.OrderCreatedEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderHistoryRepository orderHistoryRepository;
    ApplicationEventPublisher eventPublisher;

    @Override
    public OrderResponse create(OrderCreateRequest request) {
        OrderEntity order = OrderEntity.builder()
                .customerFullName(request.getCustomer().getFullName())
                .customerPhone(request.getCustomer().getPhone())
                .customerEmail(request.getCustomer().getEmail())
                .customerAddress(request.getCustomer().getAddress())
                .customerNote(request.getCustomer().getNote())
                .amount(request.getAmount())
                .status(OrderStatus.PENDING)
                .build();

        List<OrderItem> items = new ArrayList<>();
        if (request.getItems() != null) {
            for (OrderCreateRequest.Item it : request.getItems()) {
                OrderItem item = OrderItem.builder()
                        .order(order)
                        .productName(it.getName())
                        .quantity(it.getQuantity())
                        .price(it.getPrice())
                        .build();
                items.add(item);
            }
        }
        order.setItems(items);

        OrderEntity saved = orderRepository.save(order);

        // Publish domain event for notifications
        try {
            eventPublisher.publishEvent(new OrderCreatedEvent(saved.getId(), saved.getCustomerFullName(), saved.getAmount()));
        } catch (Exception ignored) {}

        return OrderResponse.builder()
                .id(saved.getId())
                .customerFullName(saved.getCustomerFullName())
                .customerPhone(saved.getCustomerPhone())
                .customerEmail(saved.getCustomerEmail())
                .customerAddress(saved.getCustomerAddress())
                .customerNote(saved.getCustomerNote())
                .amount(saved.getAmount())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .items(saved.getItems() != null ? saved.getItems().stream().map(oi -> OrderResponse.Item.builder()
                        .id(oi.getId())
                        .productName(oi.getProductName())
                        .quantity(oi.getQuantity())
                        .price(oi.getPrice())
                        .build()).toList() : List.of())
                .build();
    }

    @Override
    public Page<OrderResponse> list(int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1));
        var pageData = orderRepository.findAll(pageable);
        List<OrderResponse> mapped = pageData.getContent().stream().map(saved -> OrderResponse.builder()
                .id(saved.getId())
                .customerFullName(saved.getCustomerFullName())
                .customerPhone(saved.getCustomerPhone())
                .customerEmail(saved.getCustomerEmail())
                .customerAddress(saved.getCustomerAddress())
                .customerNote(saved.getCustomerNote())
                .amount(saved.getAmount())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .items(saved.getItems() != null ? saved.getItems().stream().map(oi -> OrderResponse.Item.builder()
                        .id(oi.getId())
                        .productName(oi.getProductName())
                        .quantity(oi.getQuantity())
                        .price(oi.getPrice())
                        .build()).toList() : List.of())
                .build()).toList();
        return new PageImpl<>(mapped, pageable, pageData.getTotalElements());
    }

    @Override
    public OrderResponse updateStatus(String orderId, String status) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Map status string to OrderStatus enum
        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }

        OrderStatus old = order.getStatus();
        order.setStatus(orderStatus);
        OrderEntity saved = orderRepository.save(order);

        // Save history entry
        OrderHistory history = OrderHistory.builder()
                .order(saved)
                .oldStatus(old)
                .newStatus(orderStatus)
                .changedBy("SYSTEM")
                .note(null)
                .build();
        orderHistoryRepository.save(history);

        return OrderResponse.builder()
                .id(saved.getId())
                .customerFullName(saved.getCustomerFullName())
                .customerPhone(saved.getCustomerPhone())
                .customerEmail(saved.getCustomerEmail())
                .customerAddress(saved.getCustomerAddress())
                .customerNote(saved.getCustomerNote())
                .amount(saved.getAmount())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .items(saved.getItems() != null ? saved.getItems().stream().map(oi -> OrderResponse.Item.builder()
                        .id(oi.getId())
                        .productName(oi.getProductName())
                        .quantity(oi.getQuantity())
                        .price(oi.getPrice())
                        .build()).toList() : List.of())
                .build();
    }

    @Override
    public List<OrderHistoryResponse> history(String orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return orderHistoryRepository.findByOrderOrderByCreatedAtDesc(order).stream()
                .map(h -> OrderHistoryResponse.builder()
                        .id(h.getId())
                        .oldStatus(h.getOldStatus())
                        .newStatus(h.getNewStatus())
                        .changedBy(h.getChangedBy())
                        .note(h.getNote())
                        .createdAt(h.getCreatedAt())
                        .build())
                .toList();
    }
}




