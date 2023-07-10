package com.assignment.productmanagementservice.auth.filter;

import com.assignment.productmanagementservice.auth.token.AuthToken;
import com.assignment.productmanagementservice.auth.token.AuthTokenProvider;
import com.assignment.productmanagementservice.auth.utils.HeaderUtil;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.response.ErrorResponder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// JWT 토큰 검증 필터 --> 유효한 토큰이면 SecurityContext 에 인증 정보 저장
public class JwtVerificationFilter extends OncePerRequestFilter {
   private final AuthTokenProvider tokenProvider;

   public JwtVerificationFilter(AuthTokenProvider tokenProvider) {
      this.tokenProvider = tokenProvider;
   }

   @Override
   protected void doFilterInternal(
           HttpServletRequest request,
           HttpServletResponse response,
           FilterChain filterChain)  throws ServletException, IOException {

      String tokenStr = HeaderUtil.getAccessToken(request);
      AuthToken token = tokenProvider.convertAuthToken(tokenStr);

      if (token.validate()) {
         Authentication authentication = tokenProvider.getAuthentication(token);
         SecurityContextHolder.getContext().setAuthentication(authentication);
      }

      // TODO blacklist에 추가된 Tocken인지 검증
//      if (token.validate() && !redisUtils.hasKeyBlackList(tokenStr)) {
//         Authentication authentication = null;
//         try {
//             authentication = tokenProvider.getAuthentication(token);
//         } catch (CustomLogicException e) {
//            ErrorResponder.sendErrorResponse(response, HttpStatus.BAD_REQUEST);
//         }
//         SecurityContextHolder.getContext().setAuthentication(authentication);
//      }

      filterChain.doFilter(request, response);
   }
   @Override
   protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
      String tokenStr = HeaderUtil.getAccessToken(request);
      return tokenStr == null;
   }
}
