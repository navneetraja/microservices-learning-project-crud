package com.example.orderservice.client;

import com.example.orderservice.dto.InventoryRequest;
import com.example.orderservice.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @PostMapping("/api/inventory/reserve")
    InventoryResponse reserve(@RequestBody InventoryRequest request);
}
