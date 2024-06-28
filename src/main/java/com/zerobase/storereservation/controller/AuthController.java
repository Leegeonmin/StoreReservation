package com.zerobase.storereservation.controller;

import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.dto.SignIn;
import com.zerobase.storereservation.dto.SignUp;
import com.zerobase.storereservation.security.TokenProvider;
import com.zerobase.storereservation.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    /**
     * 회원가입 API
     * @param request
     * request : 이름, 비밀번호, 휴대전화번호, 역할을 입력받아 회원가입
     * @return 사용자 id
     */
    @PostMapping("/signup")
    public ResponseEntity<SignUp.Response> signup(@RequestBody @Valid SignUp.Request request) {
        log.info("SignUp request: {}", request);
        Long memberId = memberService.signup(request.getName(), request.getPassword(), request.getPhone(), request.getRole());
        return ResponseEntity.ok().body(SignUp.Response.builder().userId(memberId).build());
    }

    /**
     * 로그인 API
     * @param request
     * request : 이름, 비밀번호
     * @return jwt
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody @Valid SignIn.Request request){
        log.info("SignIn request: {}", request);
        MemberEntity memberEntity = memberService.signin(request.getName(), request.getPassword());

        String token = tokenProvider.generateToken(memberEntity.getName(), memberEntity.getUserRole().name());
        return ResponseEntity.ok().body(token);

    }

}
