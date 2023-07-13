package com.assignment.productmanagementservice.domain.order.service;

import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponScope;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponType;
import com.assignment.productmanagementservice.domain.coupon.service.CouponService;
import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.order.repository.JpaOrderRepository;
import com.assignment.productmanagementservice.domain.orderItem.entity.OrderItem;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.product.service.ProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    // 주문 생성
    @Override
    public Order createOrder(Order order, String userName) {
        memberService.findMember(userName);

        // 요청으로 들어온 order에 포함된 productId가 실제 존재하는지 검증
        List<Long> productIds = findProductIds(order);
        productService.findProductsByIds(productIds);

        // 주문 총 금액 계산
        long totalAmount = calculateOrderTotalAmount(order);
        long deliveryFee = order.getDeliveryFee() != null ? order.getDeliveryFee() : 3000L;
        order.setDeliveryFee(deliveryFee); // 배달비 임의로 3000원으로 설정
        order.setTotalAmount(totalAmount); // 총 금액 설정

        // 쿠폰을 선택했다면
        if (order.getCoupon() != null) {
            long requiredAmountForPayment = calculateOrderPaymentAmount(order);
            order.setPaymentAmount(requiredAmountForPayment); // 최종 결제 금액 설정

        } else {
            order.setPaymentAmount(totalAmount);
        }

        Coupon coupon = couponService.findCoupon(order.getCoupon().getCouponId());
        order.setCoupon(coupon);

        return jpaOrderRepository.save(order);
    }

    // 주문 총 금액 계산 API
    @Override
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

    // 주문에 대한 필요 결제 금액 계산 API
    @Override
    public long calculateOrderPaymentAmount(Order order) {
        long totalDiscountAmount = 0L;

        // 쿠폰을 선택했다면
        if (order.getCoupon() != null) {
            Coupon coupon = couponService.findCoupon(order.getCoupon().getCouponId());
            totalDiscountAmount = calculateCouponDiscountAmount(coupon.getCouponId(), order);
        }

        // 결제해야할 최종 금액 = 총 주문 금액 - 쿠폰 할인 금액
        long requiredAmountForPayment = order.getTotalAmount() - totalDiscountAmount;

        return requiredAmountForPayment;
    }

    // 쿠폰의 할인 금액을 계산하는 로직
    private long calculateCouponDiscountAmount(Long couponId, Order order) {
        long totalDiscountAmount = 0L; // 일단 할인 가격을 0이라고 하고

        Coupon coupon = couponService.findCoupon(couponId); // 해당 쿠폰이 존재하는지 검증 후 가져오기

        // 쿠폰 적용 범위에 따라 할인 금액 계산
        CouponScope couponScope = coupon.getScope();
        switch (couponScope) {
            case FULL_ORDER:
                totalDiscountAmount = calculateDiscountForFullOrder(coupon, order);
                break;
            case SPECIFIC_PRODUCT:
                totalDiscountAmount = calculateDiscountForSpecificProduct(coupon, order);
                break;
            default:
                break;
        }

        return totalDiscountAmount;
    }

    // 주문 전체에 할인 적용 시에
    private long calculateDiscountForFullOrder(Coupon coupon, Order order) {
        long totalDiscountAmount = 0L;

        if (coupon.getType() == CouponType.RATIO) { // 비율 쿠폰인 경우
            totalDiscountAmount = (calculateOrderTotalAmount(order) - order.getDeliveryFee()) * coupon.getDiscountRate() / 100; // 할인 금액 = 주문 총 금액-배달비 * 할인율

        } else if (coupon.getType() == CouponType.FIXED) { // 고정 쿠폰인 경우
            totalDiscountAmount = coupon.getDiscountAmount();
        }

        return totalDiscountAmount;
    }

    // 특정 상품에만 할인 적용 시에
    private long calculateDiscountForSpecificProduct(Coupon coupon, Order order) {
        long totalDiscountAmount = 0L;

        Product specificProduct = productService.findProduct(coupon.getSpecificProductId());

        List<Long> productIds = findProductIds(order);
        productService.findProductsByIds(productIds);
        long specificProductCount = productIds.size();

        if (coupon.getType() == CouponType.RATIO) { // 비율 쿠폰인 경우
            totalDiscountAmount = (specificProduct.getPrice() * coupon.getDiscountRate() / 100) * specificProductCount; // (해당 상품 가격 * 할인율 / 100) * 쿠폰 적용할 특정 상품 개수

        } else if (coupon.getType() == CouponType.FIXED) { // 고정 쿠폰인 경우
            totalDiscountAmount = specificProductCount * coupon.getDiscountAmount(); // 쿠폰 적용할 특정 상품 개수 * 할인할 금액
        }

        return totalDiscountAmount;
    }

    private List<Long> findProductIds(Order order) {
        return order.getOrderItems().stream()
                .map(OrderItem::getProduct)
                .map(Product::getProductId)
                .toList();
    }

}
