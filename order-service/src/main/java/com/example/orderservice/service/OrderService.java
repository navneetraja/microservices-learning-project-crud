package com.example.orderservice.service;

import com.example.orderservice.client.InventoryClient;
import com.example.orderservice.client.PaymentClient;
import com.example.orderservice.dto.*;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final PaymentClient paymentClient;
    private final InventoryClient inventoryClient;

    public OrderService(OrderRepository repository, PaymentClient paymentClient, InventoryClient inventoryClient) {
        this.repository = repository;
        this.paymentClient = paymentClient;
        this.inventoryClient = inventoryClient;
    }

    public List<OrderResponse> findAll() {
        return repository.findAll().stream().map(OrderResponse::from).toList();
    }

    public OrderResponse findById(Long id) {
        return OrderResponse.from(get(id));
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        Order saved = repository.save(new Order(request.productId(), request.quantity(), request.amount(), "PENDING"));
        return OrderResponse.from(saved);
    }

    @Transactional
    public OrderResponse update(Long id, UpdateOrderRequest request) {
        Order order = get(id);
        if (request.productId() != null) order.setProductId(request.productId());
        if (request.quantity() != null) order.setQuantity(request.quantity());
        if (request.amount() != null) order.setAmount(request.amount());
        if (request.status() != null) order.setStatus(request.status());
        return OrderResponse.from(repository.save(order));
    }

    public void delete(Long id) {
        repository.delete(get(id));
    }

    @Transactional
    public OrderResponse placeOrder(Long id) {
        Order order = get(id);
        PaymentResponse payment = paymentClient.pay(new PaymentRequest(order.getId(), order.getAmount()));
        if (!"SUCCESS".equals(payment.status())) throw new IllegalStateException("Payment failed");
        InventoryResponse inventory = inventoryClient.reserve(new InventoryRequest(order.getProductId(), order.getQuantity()));
        if (!"RESERVED".equals(inventory.status())) throw new IllegalStateException("Inventory reservation failed");
        order.setStatus("CONFIRMED");
        return OrderResponse.from(repository.save(order));
    }

    private Order get(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + id));
    }
}
