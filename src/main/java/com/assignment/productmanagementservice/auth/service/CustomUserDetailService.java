package com.assignment.productmanagementservice.auth.service;

import com.assignment.productmanagementservice.auth.dto.MemberPrincipal;
import com.assignment.productmanagementservice.domain.member.entity.Member;
import com.assignment.productmanagementservice.domain.member.repository.JpaMemberRepository;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
   private final JpaMemberRepository memberRepository;
   public CustomUserDetailService(JpaMemberRepository memberRepository) {
      this.memberRepository = memberRepository;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Member member =  memberRepository.findByEmail(username)
              .orElseThrow(() -> new CustomLogicException(ExceptionCode.MEMBER_NONE));
         return  MemberPrincipal.create(member);
   }
}
