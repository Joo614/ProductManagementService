package com.assignment.productmanagementservice.domain.member.controller;

import com.assignment.productmanagementservice.domain.member.dto.MemberDto;
import com.assignment.productmanagementservice.domain.member.mapper.MemberMapper;
import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.grobal.response.SingleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/members")
@Validated
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private static final String BASE_URL = "/api/v1/members";

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody MemberDto.Post memberPostDto) {
        memberService.createMember(memberMapper.memberPostDtoToMember(memberPostDto));
        return ResponseEntity.created(URI.create(BASE_URL)).build();
    }

    // 회원 조회
    @GetMapping("/{email}")
    public ResponseEntity getMember(@PathVariable String email) {
        MemberDto.Response member = memberService.findMemberDto(email);
        return ResponseEntity.ok(new SingleResponse<>(member));
    }
}