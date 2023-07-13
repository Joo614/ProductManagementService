package com.assignment.productmanagementservice.domain.order.service;

import com.assignment.productmanagementservice.domain.order.entity.Order;

public interface OrderService {
    Order createOrder(Order order, String userName); // 주문 생성
    long calculateOrderTotalAmount(Order order); // 주문 총 금액 계산 (가격*수량 + 배달비)
    long calculateOrderPaymentAmount(Order order); // 총 결제 금액 계산 ((가격*수량 + 배달비) - 쿠폰 적용 금액)
}
