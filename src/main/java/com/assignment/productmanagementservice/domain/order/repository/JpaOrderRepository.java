package com.assignment.productmanagementservice.domain.order.repository;

import com.assignment.productmanagementservice.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long>, OrderRepository {
}
