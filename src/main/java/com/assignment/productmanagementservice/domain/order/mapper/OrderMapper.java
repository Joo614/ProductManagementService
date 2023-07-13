package com.assignment.productmanagementservice.domain.order.mapper;

import com.assignment.productmanagementservice.domain.order.dto.OrderRequestDto;
import com.assignment.productmanagementservice.domain.order.dto.OrderResponseDto;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.orderItem.dto.OrderItemDto;
import com.assignment.productmanagementservice.domain.orderItem.entity.OrderItem;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // post request
    default Order orderPostDtoToOrder(OrderRequestDto.OrderPost requestBody){
        if (requestBody == null) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();
        if (requestBody.getOrderItems() != null) {
//            order.orderItems(requestBody.getOrderItems().stream()
//                    .map(orderItem -> {
//                        return OrderItemDto.Post.builder()
//                                .productId(orderItem.getProductId())
//                                .quantity(orderItem.getQuantity())
//                                .build();
//                    }).collect(Collectors.toList()));
            List<OrderItem> orderItems = requestBody.getOrderItems().stream()
                    .map(orderItemDto -> {
                        OrderItem orderItem = OrderItem.builder()
                                .quantity(orderItemDto.getQuantity())
                                .build();
                        orderItem.setOrder(order.build()); // Order와 OrderItem 간의 양방향 관계 설정

                        // Product 엔티티를 참조하는 productId 필드 대신에 해당 Product의 식별자(ID)를 할당
                        Product product = new Product();
                        product.setProductId(orderItemDto.getProductId());
                        orderItem.setProduct(product);

                        return orderItem;
                    })
                    .collect(Collectors.toList());

            order.orderItems(orderItems);
        }
//        order.productId(orderItemPostDtoToOrderItem(requestBody.getProductId()));
//        order.quantity(orderItemPostDtoToOrderItem(requestBody.getQuantity()));
        order.deliveryFee(3000L);
//        order.coupons();
        return order.build();
    }

    // response
    default OrderResponseDto orderToOrderResponseDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponseDto.OrderResponseDtoBuilder response = OrderResponseDto.builder();
        if (order.getOrderId() == null) {
            response.orderId(order.getOrderId());
        }
//        response.productId(orderItemToResponse(order.getProductId()));
//        response.quantity(orderItemToResponse(order.getQuantity()));
        response.orderId(order.getOrderId());

        if (order.getOrderItems() != null) {
            response.orderItems(order.getOrderItems().stream()
                    .map(orderItem -> {
                        return OrderItemDto.Response.builder()
                                .productId(orderItem.getProduct().getProductId())
                                .quantity(orderItem.getQuantity())
                                .build();
                    }).collect(Collectors.toList()));
        }
        response.deliveryFee(order.getDeliveryFee());
        response.totalAmount(order.getTotalAmount());
        response.appliedCoupon(null); // TODO 일단 null

        return response.build();
    }
}
