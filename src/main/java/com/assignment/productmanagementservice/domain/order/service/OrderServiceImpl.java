package com.assignment.productmanagementservice.domain.order.service;

import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponScope;
import com.assignment.productmanagementservice.domain.coupon.service.CouponService;
import com.assignment.productmanagementservice.domain.coupon.service.CouponServiceImpl;
import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.order.repository.JpaOrderRepository;
import com.assignment.productmanagementservice.domain.orderItem.entity.OrderItem;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.product.service.ProductService;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final JpaOrderRepository jpaOrderRepository;
    private final MemberService memberService;
    private final ProductService productService;
    private final CouponService couponService;

    public OrderServiceImpl(JpaOrderRepository jpaOrderRepository, MemberService memberService, ProductService productService, CouponService couponService) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.memberService = memberService;
        this.productService = productService;
        this.couponService = couponService;
    }

    // 주문 생성 및 주문 총 금액 계산 API
    @Override
    public Order createOrder(Order order, String userName) {
        memberService.findMember(userName);

        // 요청으로 들어온 order에 포함된 productId가 실제 존재하는지 검증
        List<Long> productIds = order.getOrderItems().stream()
                .map(OrderItem::getProduct)
                .map(Product::getProductId)
                .toList();
        productService.findProductsByIds(productIds);

        // 주문 총 금액 계산
        long totalAmount = calculateOrderTotalAmount(order);

        long deliveryFee = order.getDeliveryFee() != null ? order.getDeliveryFee() : 3000L;
        order.setDeliveryFee(deliveryFee); // 배달비 임의로 3000원으로 설정

        order.setTotalAmount(totalAmount); // 총 금액 설정

        return jpaOrderRepository.save(order);
    }

    // 주문 총 금액 계산 API
    public long calculateOrderTotalAmount(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        // amount - 가격 * 수량 / totalAmount - 총 금액 ( amount + 배달비 )
        long amount = orderItems.stream()
                .mapToLong(orderItem -> {
                    Product product = productService.findProduct(orderItem.getProduct().getProductId());
                    long price = product.getPrice();
                    long quantity = orderItem.getQuantity();
                    return price * quantity;
                })
                .sum();

        long deliveryFee = order.getDeliveryFee() != null ? order.getDeliveryFee() : 3000L;

        return amount + deliveryFee;
    }

    // TODO 쿠폰 적용하고 십원 단위로 금액 변경
    // 주문에 대한 필요 결제 금액 계산 API
    public long calculateOrderPaymentAmount(Long orderId, Long couponId) {
        long totalDiscountAmount = 0L;
        // TODO 검증 로직 따로 빼
        Order order = jpaOrderRepository.findById(orderId)
                .orElseThrow(() -> new CustomLogicException(ExceptionCode.ORDER_NONE));

        Optional<Coupon> appliedCoupon = order.getCoupons().stream()
                .filter(coupon -> coupon.getCouponId().equals(couponId))
                .findFirst();

        if (appliedCoupon.isPresent()) {
            Coupon coupon = appliedCoupon.get();
            totalDiscountAmount = couponService.calculateCouponDiscount(coupon.getCouponId(), order);
        }

//        // 필요 결제 금액 = 총 주문 금액 - 쿠폰 할인 금액
        long requiredAmountForPayment = order.getTotalAmount() - totalDiscountAmount;

        return requiredAmountForPayment;
        // TODO 위에 그대로 말고 주문 전체 인지 특정 상품 한정인지 계산 후에 return하기
    }
}
