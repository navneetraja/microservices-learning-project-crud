package com.example.paymentservice.dto;

import com.example.paymentservice.entity.Payment;

public record PaymentResponse(Long id, Long orderId, Double amount, String status) {
    public static PaymentResponse from(Payment p) {
        return new PaymentResponse(p.getId(), p.getOrderId(), p.getAmount(), p.getStatus());
    }
}
