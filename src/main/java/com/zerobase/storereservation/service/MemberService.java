package com.zerobase.storereservation.service;

import com.zerobase.storereservation.exception.CustomException;
import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.repository.MemberRepository;
import com.zerobase.storereservation.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.zerobase.storereservation.exception.ErrorCode.USER_ALREADY_EXISTED;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * 회원가입 전 중복아이디가 있는지 검증 후 사용자 정보 저장
     * @param name
     * @param password
     * @param phone
     * @param role
     * @return
     */

    public Long signup(String name, String password, String phone, String role) {
        if(memberRepository.existsByName(name)) {
            throw new CustomException(USER_ALREADY_EXISTED);
        }

        MemberEntity memberEntity = memberRepository.save(
                MemberEntity.builder()
                        .name(name)
                        .password(passwordEncoder.encode(password))
                        .phone(phone)
                        .userRole(Objects.equals(role, "USER") ? UserRole.USER : UserRole.CEO)
                        .build()
        );
        return memberEntity.getId();
    }
}
