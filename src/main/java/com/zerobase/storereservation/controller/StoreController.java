package com.zerobase.storereservation.controller;

import com.zerobase.storereservation.dto.AddStore;
import com.zerobase.storereservation.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    /**
     * 매장등록 API
     * @param request
     * request 이름, 주소, 설명, 사장님 Id
     * @return
     */
    @PreAuthorize("hasRole('CEO')")
    @PostMapping
    public ResponseEntity<AddStore.Response> AddStore(@RequestBody @Valid  AddStore.Request request){
        Long storeId = storeService.registerStore(request.getName(), request.getDescription(),
                request.getAddress(), request.getMemberId());
        return ResponseEntity.ok().body(AddStore.Response.builder().storeId(storeId).build());
    }
}
