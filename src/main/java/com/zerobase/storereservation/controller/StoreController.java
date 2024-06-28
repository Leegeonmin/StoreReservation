package com.zerobase.storereservation.controller;

import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.dto.*;
import com.zerobase.storereservation.service.StoreService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/store")
@RequiredArgsConstructor
// XXX 기존에는 매장 등록이라면 직접 HttpBody에서 매장 MemberId를 입력받아서 입력했는데
//  UserId = 2인 CEO와, UserId = 3인 CEO가 있다고 가정하면
//  UserId = 2로 로그인하여 token을 받아온 후 addStore에서 HttpBody에서 MemberId를 3를 입력해도
//  정상적으로 입력이 되어 HttpBody에서 직접 받아오는 대신 @AuthenticationPricipal을 사용해
//  token 인증 시 사용한 정보를 가져와 넣어주는 식으로 변경했는데
//  이렇게 사용하는 것이 어떤 문제가 생길 지 모르겠어서 주석 남깁니다.
public class StoreController {
    private final StoreService storeService;


    /**
     * 매장 상세 조회
     * @param id 매장 id
     * @return 매장 이름, 매장 설명, 매장 주소
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getStoreDetail(@PathVariable Long id){
        log.info("GetStoreDetail request : {}",id);
        StoreDto storeDto = storeService.searchStoreDetail(id);
        return ResponseEntity.ok().body(
                GetStoreDetail.Request.builder()
                        .address(storeDto.getAddress())
                        .description(storeDto.getDescription())
                        .name(storeDto.getName())
                        .build()
        );
    }
    /**
     * 매장 검색 API
     * @param keyword 매장 이름에 들어갈 내용 빈값 가능
     * @param pageable page와 size가 필요
     * @return 매장의 이름, 주소, 설명이 들어감
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public ResponseEntity<Page<String>> getStores(@RequestParam(name = "keyword") @Nullable String keyword,
                                                     Pageable pageable){
        log.info("GetStores request : {}",keyword);
        Page<String> storeDtos = storeService.searchStore(keyword, pageable);

        return ResponseEntity.ok().body(storeDtos);
    }
    /**
     * 매장 등록 API
     *
     * @param request 매장이름, 매장주소, 매장설명
     * @param member  Jwt를 통해 가져온 사용자 정보
     * @return 매장ID
     */
    @PreAuthorize("hasRole('CEO')")
    @PostMapping
    public ResponseEntity<AddStore.Response> addStore(@RequestBody @Valid AddStore.Request request
            , @AuthenticationPrincipal MemberEntity member) {
        log.info("AddStore request : {}",request);
        Long storeId = storeService.registerStore(request.getName(), request.getDescription(),
                request.getAddress(), member.getId());
        return ResponseEntity.ok().body(AddStore.Response.builder().storeId(storeId).build());
    }

    /**
     * 매장 수정 API
     *
     * @param request 매장ID, 매장이름, 매장주소, 매장설명 (매장ID를 제외 전부 Nullable)
     * @param member  Jwt를 통해 가져온 사용자 정보
     * @return 수정된 매장이름
     */
    @PreAuthorize("hasRole('CEO')")
    @PatchMapping
    public ResponseEntity<String> updateStore(@RequestBody @Valid UpdateStore.Request request
            , @AuthenticationPrincipal MemberEntity member) {
        storeService.updateStore(member.getId(), StoreDto.builder()
                .storeId(request.getStoreId())
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .build());
        return ResponseEntity.ok().body("store " + request.getName() + " update successful");
    }

    /**
     * 매장 삭제 API
     * @param request storeId 입력받음
     * @param member Jwt를 통해 가져온 사용자 정보
     * @return 삭제 성공 메시지
     */
    @PreAuthorize("hasRole('CEO')")
    @DeleteMapping
    public ResponseEntity<String> deleteStore(@RequestBody @Valid DeleteStore.Request request
            , @AuthenticationPrincipal MemberEntity member) {
        String storeName = storeService.deleteStore(request.getStoreId(), member.getId());
        return ResponseEntity.ok().body("store " + storeName + " delete successful" );
    }
}
