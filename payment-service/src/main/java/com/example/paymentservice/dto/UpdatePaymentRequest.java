package com.example.paymentservice.dto;
import jakarta.validation.constraints.DecimalMin;
public record UpdatePaymentRequest(Long orderId, @DecimalMin("0.01") Double amount, String status) {}
