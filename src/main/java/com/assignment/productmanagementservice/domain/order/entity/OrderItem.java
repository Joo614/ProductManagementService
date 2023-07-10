package com.assignment.productmanagementservice.domain.order.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(nullable = false, length = 20)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;
}
