package com.example.paymentservice.controller;

import com.example.paymentservice.dto.*;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;
    private final PaymentRepository repository;

    public PaymentController(PaymentService service, PaymentRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    public List<PaymentResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PaymentResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse create(@Valid @RequestBody CreatePaymentRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public PaymentResponse update(@PathVariable Long id, @Valid @RequestBody UpdatePaymentRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // Internal endpoint used by Order Service through OpenFeign.
    @PostMapping("/process")
    public PaymentResponse process(@RequestBody PaymentRequest request) {
        Payment payment = repository.save(new Payment(request.orderId(), request.amount(), "SUCCESS"));
        return PaymentResponse.from(payment);
    }

    // Backward-compatible simple endpoint.
    @PostMapping("/charge")
    public PaymentResponse charge(@RequestBody PaymentRequest request) {
        return process(request);
    }
}
