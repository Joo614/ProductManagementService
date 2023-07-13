package com.assignment.productmanagementservice.domain.member.service;

import com.assignment.productmanagementservice.domain.member.dto.MemberDto;
import com.assignment.productmanagementservice.domain.member.entity.Member;

public interface MemberService {
    Member createMember(Member member);
    MemberDto.Response findMemberDto(String email);

    // 검증 로직
    Member findMember(String email);
    Member verifyMember(String email);
}
