package com.example.orderservice.dto;

import com.example.orderservice.entity.Order;

public record OrderResponse(Long id, Long productId, Integer quantity, Double amount, String status) {
    public static OrderResponse from(Order o) {
        return new OrderResponse(o.getId(), o.getProductId(), o.getQuantity(), o.getAmount(), o.getStatus());
    }
}
