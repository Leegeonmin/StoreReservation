package com.zerobase.storereservation.security;

import com.zerobase.storereservation.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "BEARER ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.resolveTokenFromRequest(request);

         if(token != null && tokenProvider.validateToken(token)){
             Authentication authentication = tokenProvider.getAuthentication(token);
             SecurityContextHolder.getContext().setAuthentication(authentication);
         }
        filterChain.doFilter(request,response);
    }

    private String resolveTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader(TOKEN_HEADER);
        if( !token.isEmpty() && token.startsWith(TOKEN_PREFIX)){
            return token.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
