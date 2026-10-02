package com.example.inventoryservice.dto;

import com.example.inventoryservice.entity.Inventory;

public record InventoryResponse(Long id, Long productId, Integer quantityAvailable, String status) {
    public static InventoryResponse from(Inventory i) {
        return new InventoryResponse(i.getId(), i.getProductId(), i.getQuantityAvailable(), null);
    }

    public static InventoryResponse reservation(Inventory i, String status) {
        return new InventoryResponse(i.getId(), i.getProductId(), i.getQuantityAvailable(), status);
    }
}
