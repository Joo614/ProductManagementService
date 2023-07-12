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
        List<OrderItem> orderItems = order.getOrderItems();
        long totalAmount = orderItems.stream()
                .mapToLong(orderItem -> {
                    long price = orderItem.getProduct().getPrice();
                    long quantity = orderItem.getQuantity();
                    return price * quantity;
                })
                .sum();

        // TODO
//        for (OrderItem orderItem : orderItems) {
//            Product product = orderItem.getProduct();
//            long price = product.getPrice();
//            long quantity = orderItem.getQuantity();
//
//            orderItem.setPrice(price); // OrderItem에 price 설정
//            orderItem.setTotalAmount(price * quantity); // OrderItem에 totalAmount 설정
//        }

        order.setDeliveryFee(3000L); // 배달비 임의로 3000원으로 설정
        order.setTotalAmount(totalAmount); // 총 금액 설정

        return jpaOrderRepository.save(order);
    }
}
