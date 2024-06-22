package com.zerobase.storereservation.controller;

import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.dto.AddStore;
import com.zerobase.storereservation.dto.StoreDto;
import com.zerobase.storereservation.dto.UpdateStore;
import com.zerobase.storereservation.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * 매장 등록 API
     * @param request 매장이름, 매장주소, 매장설명
     * @param member Jwt를 통해 가져온 사용자 정보
     * @return 매장ID
     */
    @PreAuthorize("hasRole('CEO')")
    @PostMapping
    public ResponseEntity<AddStore.Response> addStore(@RequestBody @Valid AddStore.Request request
            , @AuthenticationPrincipal MemberEntity member) {
        Long storeId = storeService.registerStore(request.getName(), request.getDescription(),
                request.getAddress(), member.getId());
        return ResponseEntity.ok().body(AddStore.Response.builder().storeId(storeId).build());
    }

    /**
     * 매장 수정 API
     * @param request 매장ID, 매장이름, 매장주소, 매장설명 (매장ID를 제외 전부 Nullable)
     * @param member Jwt를 통해 가져온 사용자 정보
     * @return 수정된 매장이름
     */
    @PreAuthorize("hasRole('CEO')")
    @PatchMapping
    public ResponseEntity<?> updateStore(@RequestBody @Valid UpdateStore.Request request
            , @AuthenticationPrincipal MemberEntity member) {
        storeService.updateStore(member.getId(), StoreDto.builder()
                .storeId(request.getStoreId())
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .build());
        return ResponseEntity.ok().body("store " + request.getName() + " update successful");
    }
}
