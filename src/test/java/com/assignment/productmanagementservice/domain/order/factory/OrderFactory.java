package com.assignment.productmanagementservice.domain.order.factory;

import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponScope;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponType;
import com.assignment.productmanagementservice.domain.order.dto.OrderRequestDto;
import com.assignment.productmanagementservice.domain.order.dto.OrderResponseDto;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.orderItem.dto.OrderItemDto;
import com.assignment.productmanagementservice.domain.orderItem.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderFactory {
    public static OrderRequestDto.OrderPost createOrderPost() {
        List<OrderItemDto.Post> orderItems = createOrderItemPosts();
        CouponDto.OrderPost coupon = createCouponPost();
        return OrderRequestDto.OrderPost.builder()
                .orderItems(orderItems)
                .coupon(coupon)
                .build();
    }

    public static Order createOrder() {
        List<OrderItem> orderItems = createOrderItems();
        return Order.builder()
                .orderId(1L)
                .orderItems(orderItems)
                .deliveryFee(3000L)
                .totalAmount(8000L)
                .build();
    }

    public static OrderResponseDto createOrderResponseDto(Order order) {
        List<OrderItemDto.Response> orderItems = createOrderItemResponses(order.getOrderItems());
        CouponDto.Response appliedCoupon = createCouponResponse();
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .orderItems(orderItems)
                .deliveryFee(order.getDeliveryFee())
                .totalAmount(order.getTotalAmount())
                .paymentAmount(order.getTotalAmount() - order.getDeliveryFee())
                .appliedCoupon(appliedCoupon)
                .build();
    }

    private static List<OrderItemDto.Post> createOrderItemPosts() {
        List<OrderItemDto.Post> orderItems = new ArrayList<>();
        OrderItemDto.Post orderItem1 = OrderItemDto.Post.builder()
                .productId(1L)
                .quantity(2L)
                .build();
        OrderItemDto.Post orderItem2 = OrderItemDto.Post.builder()
                .productId(2L)
                .quantity(3L)
                .build();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        return orderItems;
    }

    private static List<OrderItem> createOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = OrderItem.builder()
                .orderItemId(1L)
                .quantity(2L)
                .build();
        OrderItem orderItem2 = OrderItem.builder()
                .orderItemId(2L)
                .quantity(3L)
                .build();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        return orderItems;
    }

    private static List<OrderItemDto.Response> createOrderItemResponses(List<OrderItem> orderItems) {
        List<OrderItemDto.Response> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemDto.Response orderItemResponse = OrderItemDto.Response.builder()
                    .productId(1L)
                    .quantity(orderItem.getQuantity())
                    .build();
            orderItemResponses.add(orderItemResponse);
        }
        return orderItemResponses;
    }

    private static CouponDto.OrderPost createCouponPost() {
        return CouponDto.OrderPost.builder()
                .couponId(1L)
                .build();
    }

    private static CouponDto.Response createCouponResponse() {
        return CouponDto.Response.builder()
                .couponId(1L)
                .couponName("Coupon")
                .discountRate(10L)
                .discountAmount(1000L)
                .type(CouponType.FIXED)
                .scope(CouponScope.FULL_ORDER)
                .specificProductId(null)
                .build();
    }
}
