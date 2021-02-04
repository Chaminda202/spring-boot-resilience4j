package com.circuitebreak.order.controller;

import com.circuitebreak.order.model.OrderRequest;
import com.circuitebreak.order.model.OrderResponse;
import com.circuitebreak.order.service.OrderService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // @Bulkhead(name = "inventoryBulkhead", fallbackMethod = "placeOrderFallback")
    @ApiOperation(value = "Order Service: Place the order")
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println("Call place order method in resource layer bulkhead scenario");
        return ResponseEntity.ok().body(this.orderService.placeOrder(orderRequest));
    }

    private ResponseEntity<OrderResponse> placeOrderFallback(OrderRequest orderRequest, Throwable throwable) {
        System.out.println("Inside placeOrderFallback method in resource layer bulkhead scenario");
        final OrderResponse response = OrderResponse.builder()
                .productCode(orderRequest.getProductCode())
                .productName(orderRequest.getProductName())
                .count(orderRequest.getCount())
                .orderId("111111")
                .totalPrice(new BigDecimal(0))
                .discount(new BigDecimal(0))
                .build();
        return ResponseEntity.ok().body(response);
    }
}
