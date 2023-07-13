package com.assignment.productmanagementservice.domain.member.service;

import com.assignment.productmanagementservice.domain.member.dto.MemberDto;
import com.assignment.productmanagementservice.domain.member.entity.Member;
import com.assignment.productmanagementservice.domain.member.repository.JpaMemberRepository;
import com.assignment.productmanagementservice.domain.member.utils.AuthoritiesUtils;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final JpaMemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(JpaMemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 가입
    @Override
    public Member createMember(Member member) {
        duplicateMember(member.getEmail());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.setRoles(AuthoritiesUtils.createAuthorities(member));

        return memberRepository.save(member);
    }

    // 회원 조회
    @Override
    public MemberDto.Response findMemberDto(String email) {
        return findMember(email).toResponseDto();
    }

    // 검증 로직
    private void duplicateMember(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    throw new CustomLogicException(ExceptionCode.MEMBER_DUPLICATE);
                });
    }

    @Override
    public Member findMember(String email) {
        return verifyMember(email);
    }

    @Override
    public Member verifyMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() ->
                new CustomLogicException(ExceptionCode.MEMBER_NONE));
    }

}