package com.assignment.productmanagementservice.domain.order.service;

import com.assignment.productmanagementservice.domain.order.entity.Order;

public interface OrderService {
    Order createOrder(Order order, String userName); // 주문 생성 및 주문 총 금액 계산
}
