package com.circuitebreak.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InventoryRequest {
    private String productName;
    private String productCode;
    private int count;
    private String orderId;
}
