package com.example.paymentservice.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
public record CreatePaymentRequest(@NotNull Long orderId, @NotNull @DecimalMin("0.01") Double amount) {}
