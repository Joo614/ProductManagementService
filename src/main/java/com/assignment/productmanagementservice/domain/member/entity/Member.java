package com.assignment.productmanagementservice.domain.member.entity;

import com.assignment.productmanagementservice.domain.member.dto.MemberDto;
import com.assignment.productmanagementservice.grobal.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends Auditable {
    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 100) // 이메일 식별자
    private String email;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 100) // 이름
    private String name;
    @Column(nullable = false, length = 50) // 보여질 닉네임
    private String nickname;
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Gender genders;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AuthoritiesEntity> roles;

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

    public enum MemberRole {
        ADMIN, // MART 권한
        CUSTOMER;
    }

    public enum Gender {
        MAN("남자"),
        WOMAN("여자");

        Gender(String gender) {
            this.gender = gender;
        }

        @Getter
        private String gender;
    }

    public  MemberDto.Response toResponseDto(){
        return MemberDto.Response.builder()
                .email(email)
                .name(name)
                .genders(genders)
                .memberStatus(memberStatus)
                .nickname(nickname)
                .build();

    }
}
