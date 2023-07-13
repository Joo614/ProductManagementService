package com.assignment.productmanagementservice.domain.productPriceHistory.entity;

import com.assignment.productmanagementservice.grobal.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
// TODO indexing
public class ProductPriceHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productPriceHistoryId;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false, length = 50)
    private String productName;
    @Column(nullable = false, length = 10)
    private Long price;
}
