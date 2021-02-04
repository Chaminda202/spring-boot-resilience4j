package com.circuitebreak.order.service.impl;

import com.circuitebreak.order.config.ApplicationProperties;
import com.circuitebreak.order.model.InventoryRequest;
import com.circuitebreak.order.model.InventoryResponse;
import com.circuitebreak.order.model.OrderRequest;
import com.circuitebreak.order.model.OrderResponse;
import com.circuitebreak.order.service.OrderService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final RestTemplate restTemplate;
    private final ApplicationProperties applicationProperties;

    @Bulkhead(name = "inventoryBulkhead", fallbackMethod = "placeOrderFallback")
    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        InventoryRequest request = InventoryRequest.builder()
                .productCode(orderRequest.getProductCode())
                .productName(orderRequest.getProductName())
                .count(orderRequest.getCount())
                .orderId(UUID.randomUUID().toString())
                .build();

        HttpHeaders header = new HttpHeaders();
        HttpEntity<InventoryRequest> entity = new HttpEntity<>(request, header);
        ResponseEntity<InventoryResponse> responseEntity = this.restTemplate.exchange(applicationProperties.getInventoryEndpoint(),
                HttpMethod.POST, entity, InventoryResponse.class);
        InventoryResponse inventoryResponse = responseEntity.getBody();

        return OrderResponse.builder()
                .productCode(inventoryResponse.getProductCode())
                .productName(inventoryResponse.getProductName())
                .count(inventoryResponse.getCount())
                .orderId(inventoryResponse.getOrderId())
                .totalPrice(inventoryResponse.getTotalPrice())
                .discount(inventoryResponse.getDiscount())
                .build();
    }

    private OrderResponse placeOrderFallback(OrderRequest orderRequest, Throwable throwable) {
        System.out.println("Inside placeOrderFallback method");
        return OrderResponse.builder()
                .productCode(orderRequest.getProductCode())
                .productName(orderRequest.getProductName())
                .count(orderRequest.getCount())
                .orderId("111111")
                .totalPrice(new BigDecimal(0))
                .discount(new BigDecimal(0))
                .build();
    }
}
