package com.circuitebreak.order.service.impl;

import com.circuitebreak.order.config.ApplicationProperties;
import com.circuitebreak.order.model.InventoryRequest;
import com.circuitebreak.order.model.InventoryResponse;
import com.circuitebreak.order.model.OrderRequest;
import com.circuitebreak.order.model.OrderResponse;
import com.circuitebreak.order.service.OrderService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final RestTemplate restTemplate;
    private final ApplicationProperties applicationProperties;

    @Retry(name = "placeOrder", fallbackMethod = "placeOrderFallback")
    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        LOGGER.info("Inside placeOrder method, payload - {}", orderRequest.toString());
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
        LOGGER.info("Third party response, payload - {}", inventoryResponse.toString());
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
        LOGGER.info("Inside placeOrderFallback method, cause - {}", throwable.toString());
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
