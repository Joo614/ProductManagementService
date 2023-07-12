package com.assignment.productmanagementservice.domain.order.mapper;

import com.assignment.productmanagementservice.domain.order.dto.OrderRequestDto;
import com.assignment.productmanagementservice.domain.order.dto.OrderResponseDto;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.orderItem.dto.OrderItemDto;
import com.assignment.productmanagementservice.domain.orderItem.entity.OrderItem;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // post request
    default Order orderPostDtoToOrder(OrderRequestDto.OrderPost requestBody){
        if (requestBody == null) {
            return null;
        }

        List<OrderItemDto> orderItemDtos = requestBody.getOrderItems();

        Order order = new Order();

        List<OrderItem> orderItems = orderItemDtos.stream()
                .map(this::orderItemDtoToOrderItem)
                .toList();

        order.setOrderItems(orderItems);
        return order;
    }
    default OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();

        Product product = new Product();
        product.setProductId(orderItemDto.getProductId());
//        product.setPrice(orderItemDto);

        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemDto.getQuantity());

        return orderItem;
    }

    // response
    default OrderResponseDto orderToOrderResponseDto(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItemDto> orderItemDtos = orderItems.stream()
                .map(this::orderItemToOrderItemDto)
                .toList();

        return OrderResponseDto.builder()
                .orderItems(orderItemDtos)
                .deliveryFee(order.getDeliveryFee())
                .totalAmount(order.getTotalAmount())
                .build();
    }

    default OrderItemDto orderItemToOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productId(orderItem.getProduct().getProductId())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
