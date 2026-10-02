package com.example.orderservice.dto;
public record PaymentRequest(Long orderId, Double amount) {}
