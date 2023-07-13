package com.assignment.productmanagementservice.domain.coupon.entity;

import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.grobal.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coupon extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(nullable = false, length = 50)
    private String CouponName;

    @Column(nullable = false, length = 10)
    private Long deliveryFee;

    @Column(nullable = false)
    private BigDecimal discountRate; // 할인율 == 쿠폰 적용 비율

    @Column(nullable = false)
    private BigDecimal discountAmount; // 할인할 금액 == 쿠폰 적용 금액

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponType type;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponScope scope;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product specificProduct;

    @ManyToOne
    @JoinColumn(name = "order_id") // 주문과 연결되어야 함
    private Order order;

}
