package com.assignment.productmanagementservice.domain.member.entity;

import com.assignment.productmanagementservice.grobal.audit.Auditable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
public class AuthoritiesEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_email")
    @JsonSerialize
    private Member member;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Member.MemberRole role;

    public AuthoritiesEntity() {
    }

    public AuthoritiesEntity(Member member, String role) {
        this.member = member;
        this.role = Member.MemberRole.valueOf(role);
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
