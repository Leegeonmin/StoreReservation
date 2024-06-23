package com.zerobase.storereservation.service;

import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.domain.StoreEntity;
import com.zerobase.storereservation.dto.StoreDto;
import com.zerobase.storereservation.exception.CustomException;
import com.zerobase.storereservation.exception.ErrorCode;
import com.zerobase.storereservation.repository.MemberRepository;
import com.zerobase.storereservation.repository.StoreRepository;
import com.zerobase.storereservation.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본값을 true로 설정하여 Dirty Checking X 변경이 있는 메서드만 따로 사용할 것

public class StoreService {
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    /**
     * 매장 등록
     * memberId가 유효한지, CEO가 맞는지 검증 후 매장 정보 저장
     * @param name 매장 이름
     * @param description 매장 설명
     * @param address 매장 주소
     * @param memberId CEO의 id
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


    /**
     * 매장 수정
     * storeId가 유효한지, storeid로 store의 memberId조회, memberId와 일치하는 지 검증 후 매장 수정
     * @param memberId Jwt에서 가져온 사용자Id
     * @param storeDto storeId, 매장이름, 매장주소, 매장설명
     */
    @Transactional(readOnly = false)
    public void updateStore(Long memberId, StoreDto storeDto) {
        StoreEntity storeEntity = validatedStore(storeDto.getStoreId(), memberId);

        storeEntity.update(storeDto);

    }

    /**
     * 매장 삭제
     * 매장id, 사용자id 검증 후 삭제
     * @param storeId 매장 ID
     * @param memberId
     * @return
     */
    @Transactional(readOnly = false)
    public String deleteStore(Long storeId, Long memberId) {
        StoreEntity storeEntity = validatedStore(storeId, memberId);
        storeRepository.delete(storeEntity);
        return storeEntity.getName();
    }


    /**
     * 상점 수정, 삭제 시 공통 유효성 검증 로직
     * storeID가 유효한지, store의 MemberId와 입력의 MemberId가 일치하는지 검증
     * @param storeId 매장 id
     * @param memberId CEO id
     * @return StoreEntity
     */
    private StoreEntity validatedStore(Long storeId, Long memberId) {
        StoreEntity storeEntity = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomException(ErrorCode.STORE_NOT_FOUND)
        );
        if(!Objects.equals(memberId, storeEntity.getMemberId())){
            throw new CustomException(ErrorCode.CEO_UNMATCHED);
        }
        return  storeEntity;
    }


    /**
     * 매장 검색 로직
     * keyword의 존재유무에 따라서
     * @param keyword 이름에 들어갈 키워드
     * @param pageable paging에 필요한 데이터
     * @return StoreDto
     */
    public Page<StoreDto> searchStore(String keyword, Pageable pageable) {
        Page<StoreEntity> storeEntities;

        if (keyword == null || keyword.isEmpty()) {
            storeEntities = storeRepository.findAll(pageable);
        } else {
            storeEntities = storeRepository.findByNameContaining(keyword, pageable);
        }

        List<StoreDto> dtos = storeEntities.getContent().stream().map(
                entity -> StoreDto.builder()
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .address(entity.getAddress())
                        .build()
        ).collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, storeEntities.getTotalElements());
    }
}
