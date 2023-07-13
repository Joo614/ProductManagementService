package com.assignment.productmanagementservice.domain.coupon.controller;

import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.coupon.mapper.CouponMapper;
import com.assignment.productmanagementservice.domain.coupon.service.CouponService;
import com.assignment.productmanagementservice.domain.order.dto.OrderRequestDto;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.order.mapper.OrderMapper;
import com.assignment.productmanagementservice.domain.order.service.OrderService;
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
@RequestMapping("/api/v1/coupons")
public class CouponController {
    private final CouponService couponService;
    private final CouponMapper mapper;
    private static final String BASE_URL = "/api/v1/coupons"; // TODO 사용 안하면 지우기

    public CouponController(CouponService couponService, CouponMapper mapper) {
        this.couponService = couponService;
        this.mapper = mapper;
    }

    // 쿠폰 생성
    // TODO securityConfig 에 admin만 가능하도록 권한 설정
    @PostMapping()
    public ResponseEntity postCoupon(@AuthenticationPrincipal User authMember,
                                    @Valid @RequestBody CouponDto.CouponPost requestBody) {
         Coupon coupon= couponService.createCoupon(mapper.couponPostDtoToCoupon(requestBody), authMember.getUsername());
//        return ResponseEntity.created(URI.create(BASE_URL)).build(); // TODO
        return new ResponseEntity<>(new SingleResponse<>(mapper.couponToCouponResponseDto(coupon)), HttpStatus.CREATED);
    }
}
