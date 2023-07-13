package com.assignment.productmanagementservice.domain.order.service;

import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.order.repository.JpaOrderRepository;
import com.assignment.productmanagementservice.domain.orderItem.entity.OrderItem;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.product.service.ProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final JpaOrderRepository jpaOrderRepository;
    private final MemberService memberService;
    private final ProductService productService;

    public OrderServiceImpl(JpaOrderRepository jpaOrderRepository, MemberService memberService, ProductService productService) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.memberService = memberService;
        this.productService = productService;
    }

    @Override
    public Order createOrder(Order order, String userName) {
        memberService.findMember(userName);

        List<Long> productIds = order.getOrderItems().stream()
                .map(OrderItem::getProduct)
                .map(Product::getProductId)
                .toList();
        productService.findProductsByIds(productIds);

        // 주문 총 금액 계산
        // amount - 가격 * 수량 / totalAmount - 총 금액 ( amount + 배달비 )
        List<OrderItem> orderItems = order.getOrderItems();
        long amount = orderItems.stream()
                .mapToLong(orderItem -> {
                    Product product = productService.findProduct(orderItem.getProduct().getProductId());
                    long price = product.getPrice();
                    long quantity = orderItem.getQuantity();
                    return price * quantity;
                })
                .sum();

        long deliveryFee = order.getDeliveryFee() != null ? order.getDeliveryFee() : 3000L;
        order.setDeliveryFee(deliveryFee); // 배달비 임의로 3000원으로 설정

        long totalAmount = amount + deliveryFee;
        order.setTotalAmount(totalAmount); // 총 금액 설정

        return jpaOrderRepository.save(order);
    }
}
