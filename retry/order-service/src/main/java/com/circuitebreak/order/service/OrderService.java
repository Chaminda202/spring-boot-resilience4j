package com.circuitebreak.order.service;

import com.circuitebreak.order.model.OrderRequest;
import com.circuitebreak.order.model.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);
}
