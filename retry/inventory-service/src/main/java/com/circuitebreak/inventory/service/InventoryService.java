package com.circuitebreak.inventory.service;

import com.circuitebreak.inventory.model.InventoryRequest;
import com.circuitebreak.inventory.model.InventoryResponse;

public interface InventoryService {
    InventoryResponse checkInventory(InventoryRequest inventoryRequest);
}
