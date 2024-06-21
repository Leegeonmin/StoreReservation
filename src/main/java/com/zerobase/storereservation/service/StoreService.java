package com.zerobase.storereservation.service;

import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.domain.StoreEntity;
import com.zerobase.storereservation.exception.CustomException;
import com.zerobase.storereservation.exception.ErrorCode;
import com.zerobase.storereservation.repository.MemberRepository;
import com.zerobase.storereservation.repository.StoreRepository;
import com.zerobase.storereservation.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본값을 true로 설정하여 Dirty Checking X 변경이 있는 메서드만 따로 사용할 것

public class StoreService {
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    /**
     * 매장 등록
     * memberId가 유효한지, CEO가 맞는지 검증 후 매장 정보 저장
     * @param name
     * @param description
     * @param address
     * @param memberId
     * @return
     */
    @Transactional(readOnly = false)
    public Long registerStore(String name, String description, String address, Long memberId) {
        // XXX 1. security를 통해 인증을 구현하는데 그 때 토큰에서 유저정보를 가져와 등록에 활용하는 방식과
        //  이 메서드처럼 HttpBody에서 MemberId를 직접 받아 등록에 활용하는방식 중 뭐가 더 나은지 모르겠습니다
        // XXX 2. 그리고 이 메서드처럼 사용한다고 가정할 경우 우선 Security와 Jwt로 유저의 권한(CEO)은 인증이 된 상태일텐데
        //  이 메서드에서 MemberId의 유효성을 검증하는 것이 맞을까요?(memberId가 존재하는지, memberId의 유저가 CEO인지 등)

        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if(memberEntity.getUserRole() != UserRole.CEO){
            throw new CustomException(ErrorCode.USER_AUTHORIZATION_FAIL);
        }

        StoreEntity store = storeRepository.save(
                StoreEntity.builder()
                        .name(name)
                        .description(description)
                        .address(address)
                        .memberId(memberId)
                        .build()
        );

        return store.getId();
    }
    // 수정

    // 삭제
}
