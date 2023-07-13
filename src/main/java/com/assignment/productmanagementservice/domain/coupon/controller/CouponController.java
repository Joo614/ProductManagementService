package com.assignment.productmanagementservice.domain.coupon.controller;

import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.coupon.mapper.CouponMapper;
import com.assignment.productmanagementservice.domain.coupon.service.CouponService;
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

    public CouponController(CouponService couponService, CouponMapper mapper) {
        this.couponService = couponService;
        this.mapper = mapper;
    }

    // 쿠폰 생성
    @PostMapping()
    public ResponseEntity postCoupon(@AuthenticationPrincipal User authMember,
                                    @Valid @RequestBody CouponDto.CouponPost requestBody) {
         Coupon coupon= couponService.createCoupon(mapper.couponPostDtoToCoupon(requestBody), authMember.getUsername());
        return new ResponseEntity<>(new SingleResponse<>(mapper.couponToCouponResponseDto(coupon)), HttpStatus.CREATED);
    }
}
