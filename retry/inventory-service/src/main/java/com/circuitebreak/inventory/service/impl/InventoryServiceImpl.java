package com.circuitebreak.inventory.service.impl;

import com.circuitebreak.inventory.model.InventoryRequest;
import com.circuitebreak.inventory.model.InventoryResponse;
import com.circuitebreak.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InventoryServiceImpl implements InventoryService {
    static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);
    private static final BigDecimal UNIT_PRICE = new BigDecimal(12.45);
    private static final BigDecimal DISCOUNT_PERCENTAGE =  new BigDecimal(.15);
    @Override
    public InventoryResponse checkInventory(InventoryRequest inventoryRequest) {
        LOGGER.info("Inside check inventory method, payload - {}", inventoryRequest.toString());
        return InventoryResponse.builder()
                .productCode(inventoryRequest.getProductCode())
                .productName(inventoryRequest.getProductName())
                .count(inventoryRequest.getCount())
                .orderId(inventoryRequest.getOrderId())
                .totalPrice(UNIT_PRICE.subtract(UNIT_PRICE.multiply(DISCOUNT_PERCENTAGE)).multiply(new BigDecimal(inventoryRequest.getCount())))
                .discount(UNIT_PRICE.multiply(DISCOUNT_PERCENTAGE).multiply(new BigDecimal(inventoryRequest.getCount())))
                .build();
    }
}
