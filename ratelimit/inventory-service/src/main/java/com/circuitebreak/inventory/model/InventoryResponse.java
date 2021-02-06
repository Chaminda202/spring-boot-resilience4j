package com.circuitebreak.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private String productName;
    private String productCode;
    private int count;
    private String orderId;
    private BigDecimal totalPrice;
    private BigDecimal discount;
}
