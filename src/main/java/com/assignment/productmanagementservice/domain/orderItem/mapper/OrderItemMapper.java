package com.assignment.productmanagementservice.domain.orderItem.mapper;

import com.assignment.productmanagementservice.domain.orderItem.dto.OrderItemDto;
import com.assignment.productmanagementservice.domain.orderItem.entity.OrderItem;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import org.mapstruct.Mapper;

// TODO 없앨지 고민
@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    default OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();

        Product product = new Product();
        product.setProductId(orderItemDto.getProductId());

        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemDto.getQuantity());

        return orderItem;
    }
}
