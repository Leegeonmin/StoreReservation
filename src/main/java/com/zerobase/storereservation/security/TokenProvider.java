package com.zerobase.storereservation.security;

import com.zerobase.storereservation.service.MemberService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {
    private final MemberService memberService;
    public Key key;
    public Long expiredTime;

    // config에 저장한 값을 디코딩해 HMAC 알고리즘으로 Key 객체 생성
    public TokenProvider(@Value("${jwt.secretKey}") String secretKey, MemberService memberService,
                         @Value("${jwt.expiredTime}") Long accessTime) {
        this.memberService = memberService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expiredTime = accessTime;
    }

    /**
     * Jwt 토큰 생성
     * @param username 이름
     * @param role 권한
     * @return jwt
     */
    public String generateToken(String username, String role){
        log.info("generate token : username = {}, role = {}", username, role);
        Claims claims = Jwts.claims().setSubject(username);

        claims.put("role", role);

        Date date = new Date();
        Date expiredDate = new Date(date.getTime() + expiredTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(expiredDate)
                .signWith(this.key, SignatureAlgorithm.HS512)
                .compact();
    }


    /**
     * Token에서 username 추출
     * @param token jwt
     * @return jwt에서 추출한 username
     */
    public String getUsername(String token){
        return this.parseClaims(token).getSubject();
    }
    /**
     * Jwt Claims 추출
     * @param token jwt
     * @return Claims
     */
    public Claims parseClaims(String token){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            throw e;
        }
    }

    /**
     * Token 유효성 검증
     * @param token 토큰
     * @return 유효성
     */
    public boolean validateToken(String token){
        log.info("validate token : token = {}", token);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token is invalid.");
        }
        return false;
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = memberService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }
}
