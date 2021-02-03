package com.circuitebreak.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private String productName;
    private String productCode;
    private int count;
    private String orderId;
    private BigDecimal totalPrice;
    private BigDecimal discount;
}
