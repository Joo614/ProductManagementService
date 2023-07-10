package com.assignment.productmanagementservice.domain.order.entity;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // TODO fetch type 다시 함 봐
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false, length = 10)
    private Long deliveryFee;
}
