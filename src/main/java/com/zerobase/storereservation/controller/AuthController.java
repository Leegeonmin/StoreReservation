package com.zerobase.storereservation.controller;

import com.zerobase.storereservation.dto.SignUp;
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

    /**
     * 회원가입 API
     * @param request
     * @request : 이름, 비밀번호, 휴대전화번호, 역할을 입력받아 회원가입
     * @return 사용자 id
     */
    @PostMapping("/signup")
    public ResponseEntity<SignUp.Response> signup(@RequestBody @Valid SignUp.Request request) {
        log.info("signup");
        Long memberId = memberService.signup(request.getName(), request.getPassword(), request.getPhone(), request.getRole());
        return ResponseEntity.ok().body(SignUp.Response.builder().userId(memberId).build());
    }
}
