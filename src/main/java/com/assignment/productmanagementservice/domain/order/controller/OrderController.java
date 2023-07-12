package com.assignment.productmanagementservice.domain.order.controller;

import com.assignment.productmanagementservice.domain.order.dto.OrderRequestDto;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.order.mapper.OrderMapper;
import com.assignment.productmanagementservice.domain.order.service.OrderService;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.grobal.response.SingleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper mapper;
    private static final String BASE_URL = "/api/v1/orders"; // TODO 사용 안하면 지우기

    public OrderController(OrderService orderService, OrderMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    // 주문 생성
    @PostMapping()
    public ResponseEntity postOrder(@AuthenticationPrincipal User authMember,
                                    @Valid @RequestBody OrderRequestDto.OrderPost requestBody) {
        Order order = orderService.createOrder(mapper.orderPostDtoToOrder(requestBody), authMember.getUsername());
//        return ResponseEntity.created(URI.create(BASE_URL)).build(); // TODO
        return new ResponseEntity<>(new SingleResponse<>(mapper.orderToOrderResponseDto(order)), HttpStatus.CREATED);
    }
}
