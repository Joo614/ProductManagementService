package com.assignment.productmanagementservice.auth.controller;

import com.assignment.productmanagementservice.auth.service.RefreshService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Validated
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final RefreshService refreshService;


    public AuthController(RefreshService refreshService) {
        this.refreshService = refreshService;
    }
    @PostMapping("/refresh")
    public ResponseEntity refresh(HttpServletRequest request , HttpServletResponse response){
        refreshService.refresh(request, response);
        return ResponseEntity.ok().build();
    }
    // TODO 로그아웃
//    @PostMapping("/logout")
//    public ResponseEntity logout(HttpServletRequest request , HttpServletResponse response){
//        refreshService.logout(request, response);
//        return ResponseEntity.ok().build();
//    }
}
