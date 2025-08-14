package com.shopdev.controller;

import com.shopdev.dto.request.OrderCreateRequest;
import com.shopdev.dto.response.OrderResponse;
import com.shopdev.dto.response.OrderHistoryResponse;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/api/orders")
public class OrderController {
    OrderService orderService;

    @PostMapping
    public ResponseData<OrderResponse> create(@RequestBody OrderCreateRequest request) {
        return new ResponseData<>(HttpStatus.CREATED, "Created", orderService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseData<Object> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        var result = orderService.list(page, size);
        // Return a simple pagination payload for flexibility
        var payload = new java.util.HashMap<String, Object>();
        payload.put("content", result.getContent());
        payload.put("page", result.getNumber());
        payload.put("size", result.getSize());
        payload.put("totalElements", result.getTotalElements());
        payload.put("totalPages", result.getTotalPages());
        payload.put("first", result.isFirst());
        payload.put("last", result.isLast());
        return new ResponseData<>(HttpStatus.OK, "OK", payload);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}")
    public ResponseData<OrderResponse> updateStatus(
            @PathVariable String orderId,
            @RequestBody UpdateOrderStatusRequest request
    ) {
        return new ResponseData<>(HttpStatus.OK, "Updated", orderService.updateStatus(orderId, request.getStatus()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{orderId}/history")
    public ResponseData<List<OrderHistoryResponse>> history(@PathVariable String orderId) {
        return new ResponseData<>(HttpStatus.OK, "OK", orderService.history(orderId));
    }

    public static class UpdateOrderStatusRequest {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}


