package com.example.inventoryservice.dto;

public record InventoryRequest(Long productId, Integer quantity) {
}
