package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.*;
import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<InventoryResponse> findAll() {
        return repository.findAll().stream().map(InventoryResponse::from).toList();
    }

    public InventoryResponse findById(Long id) {
        return InventoryResponse.from(get(id));
    }

    @Transactional
    public InventoryResponse create(CreateInventoryRequest r) {
        return InventoryResponse.from(repository.save(new Inventory(r.productId(), r.quantityAvailable())));
    }

    @Transactional
    public InventoryResponse update(Long id, UpdateInventoryRequest r) {
        Inventory i = get(id);
        if (r.quantityAvailable() != null) i.setQuantityAvailable(r.quantityAvailable());
        return InventoryResponse.from(repository.save(i));
    }

    public void delete(Long id) {
        repository.delete(get(id));
    }

    @Transactional
    public InventoryResponse reserve(InventoryRequest r) {
        Inventory i = repository.findByProductId(r.productId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found for product: " + r.productId()));
        if (r.quantity() == null || r.quantity() <= 0) return InventoryResponse.reservation(i, "INVALID");
        if (i.getQuantityAvailable() < r.quantity()) return InventoryResponse.reservation(i, "INSUFFICIENT_STOCK");
        i.setQuantityAvailable(i.getQuantityAvailable() - r.quantity());
        repository.save(i);
        return InventoryResponse.reservation(i, "RESERVED");
    }

    private Inventory get(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found: " + id));
    }
}
