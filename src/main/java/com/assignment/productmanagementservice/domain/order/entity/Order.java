package com.assignment.productmanagementservice.domain.order.entity;

import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.orderItem.entity.OrderItem;
import com.assignment.productmanagementservice.grobal.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "orders")
public class Order extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) // TODO fetch type 다시 함 봐
    private List<OrderItem> orderItems = new ArrayList<>();
//    @OneToOne(cascade = CascadeType.ALL)
//    private OrderItem productId;
//    @OneToOne(cascade = CascadeType.ALL)
//    private OrderItem quantity;

    @Column(length = 10) // TODO nullable = false - 이거 하니까 에러가 나네 어디지
    private Long deliveryFee;

    @Column( length = 10) // TODO nullable = false - 이거 하니까 에러가 나네 어디지
    private Long totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // TODO fetch type 다시 함 봐
    private List<Coupon> coupons = new ArrayList<>();
}
