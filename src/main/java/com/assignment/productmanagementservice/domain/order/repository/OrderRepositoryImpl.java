package com.assignment.productmanagementservice.domain.order.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class OrderRepositoryImpl implements OrderRepository {
}
