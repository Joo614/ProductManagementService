package com.assignment.productmanagementservice.domain.member.repository;

import com.assignment.productmanagementservice.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, String>, MemberRepository {
    Optional<Member> findByEmail(String email);
}

