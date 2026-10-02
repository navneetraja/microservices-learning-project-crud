package com.example.paymentservice.service;

import com.example.paymentservice.dto.*;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<PaymentResponse> findAll() {
        return repository.findAll().stream().map(PaymentResponse::from).toList();
    }

    public PaymentResponse findById(Long id) {
        return PaymentResponse.from(get(id));
    }

    @Transactional
    public PaymentResponse create(CreatePaymentRequest r) {
        return PaymentResponse.from(repository.save(new Payment(r.orderId(), r.amount(), "SUCCESS")));
    }

    @Transactional
    public PaymentResponse update(Long id, UpdatePaymentRequest r) {
        Payment p = get(id);
        if (r.orderId() != null) p.setOrderId(r.orderId());
        if (r.amount() != null) p.setAmount(r.amount());
        if (r.status() != null) p.setStatus(r.status());
        return PaymentResponse.from(repository.save(p));
    }

    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Payment get(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found: " + id));
    }
}
