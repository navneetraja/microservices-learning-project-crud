package com.example.inventoryservice.dto;

import jakarta.validation.constraints.Min;

public record UpdateInventoryRequest(@Min(0) Integer quantityAvailable) {
}
