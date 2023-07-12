package com.assignment.productmanagementservice.domain.member.service;

import com.assignment.productmanagementservice.domain.member.dto.MemberDto;
import com.assignment.productmanagementservice.domain.member.entity.Member;

public interface MemberService {
    Member createMember(Member member);

    Member findMember(String email);
    MemberDto.Response findMemberDto(String email);
    Member verifyMember(String email);
}
