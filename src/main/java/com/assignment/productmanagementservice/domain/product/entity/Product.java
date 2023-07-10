package com.assignment.productmanagementservice.domain.product.entity;

import com.assignment.productmanagementservice.grobal.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
// TODO indexing 하는 거 알아보기
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, length = 50)
    private String productName;

    @Column(nullable = false, length = 10)
    private Long price;
}
