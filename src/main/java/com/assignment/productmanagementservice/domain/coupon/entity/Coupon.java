package com.assignment.productmanagementservice.domain.coupon.entity;

import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.grobal.audit.Auditable;
import lombok.*;

import javax.persistence.*;

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
    private String couponName;

    @Column
    private Long discountRate; // 할인율 == 쿠폰 적용 비율

    @Column
    private Long discountAmount; // 할인할 금액 == 쿠폰 적용 금액

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponType type;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponScope scope;

    @Column(name = "product_id")
    private Long specificProductId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
