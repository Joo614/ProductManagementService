package com.assignment.productmanagementservice.domain.order.mapper;

import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.order.dto.OrderRequestDto;
import com.assignment.productmanagementservice.domain.order.dto.OrderResponseDto;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.orderItem.dto.OrderItemDto;
import com.assignment.productmanagementservice.domain.orderItem.entity.OrderItem;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    default Order orderPostDtoToOrder(OrderRequestDto.OrderPost requestBody){
        if (requestBody == null) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        if (requestBody.getOrderItems() != null) {
            List<OrderItem> orderItems = requestBody.getOrderItems().stream()
                    .map(orderItemDto -> {
                        OrderItem orderItem = OrderItem.builder()
                                .quantity(orderItemDto.getQuantity())
                                .build();
                        orderItem.setOrder(order.build()); // Order와 OrderItem 간의 양방향 관계 설정
                        // TODO 여기 주석 지우기

                        // Product 엔티티를 참조하는 productId 필드 대신에 해당 Product의 식별자(ID)를 할당
                        Product product = new Product();
                        product.setProductId(orderItemDto.getProductId());
                        orderItem.setProduct(product);

                        return orderItem;
                    })
                    .collect(Collectors.toList());

            order.orderItems(orderItems);
        }

        order.deliveryFee(3000L);
        if (requestBody.getCoupon() != null) {
            Coupon coupon = new Coupon();
            coupon.setCouponId(requestBody.getCoupon().getCouponId());
            order.coupon(coupon);
        }
        return order.build();
    }


    default OrderResponseDto orderToOrderResponseDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponseDto.OrderResponseDtoBuilder response = OrderResponseDto.builder();
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
        response.paymentAmount(order.getPaymentAmount());

        // TODO 다시 한 번 훑어 보기
        if (order.getCoupon() != null) {
            CouponDto.Response.ResponseBuilder coupon = CouponDto.Response.builder()
                    .couponId(order.getCoupon().getCouponId())
                    .couponName(order.getCoupon().getCouponName())
                    .discountRate(order.getCoupon().getDiscountRate())
                    .discountAmount(order.getCoupon().getDiscountAmount())
                    .type(order.getCoupon().getType())
                    .scope(order.getCoupon().getScope());

            Long specificProductId = order.getCoupon().getSpecificProductId();
            if (specificProductId != null) {
                coupon.specificProductId(specificProductId);
            }
            response.appliedCoupon(coupon.build());
        }

        return response.build();
    }
}
