package com.example.orderservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

public record UpdateOrderRequest(
        Long productId,
        @Min(1) Integer quantity,
        @DecimalMin("0.01") Double amount,
        String status) {}
