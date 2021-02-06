package com.circuitebreak.inventory.controller;

import com.circuitebreak.inventory.model.InventoryRequest;
import com.circuitebreak.inventory.model.InventoryResponse;
import com.circuitebreak.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> placeOrder(@RequestBody InventoryRequest inventoryRequest) {
        return ResponseEntity.ok().body(this.inventoryService.checkInventory(inventoryRequest));
    }
}
