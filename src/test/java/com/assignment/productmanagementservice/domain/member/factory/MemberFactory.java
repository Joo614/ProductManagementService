package com.assignment.productmanagementservice.domain.member.factory;

import com.assignment.productmanagementservice.domain.member.dto.MemberDto;
import com.assignment.productmanagementservice.domain.member.entity.Member;
import com.assignment.productmanagementservice.domain.member.utils.AuthoritiesUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberFactory {
    public static MemberDto.Post createMemberPostDto() {

        return MemberDto.Post.builder()
                .email("test@gmail.com")
                .password("password")
                .name("이름이")
                .nickname("nickname")
                .genders(Member.Gender.MAN)
                .build();
    }

    public static Member createMember(PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .nickname("nickname")
                .memberStatus(Member.MemberStatus.MEMBER_ACTIVE)
                .genders(Member.Gender.MAN)
                .build();
        member.setRoles(AuthoritiesUtils.createAuthorities(member));
        return member;
    }

    public static MemberDto.Response createMemberResponseDto(Member member) {
        return MemberDto.Response.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .memberStatus(member.getMemberStatus())
                .genders(member.getGenders())
                .memberRole(Member.MemberRole.CUSTOMER)
                .build();
    }

}