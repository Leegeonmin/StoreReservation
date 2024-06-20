package com.zerobase.storereservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "Store")
@EntityListeners(AuditingEntityListener.class)
public class StoreEntity {
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private Long memberId;
    @Column(length = 100)
    @NotNull
    private String name;
    @NotNull
    private String address;
    private String description;

    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;
    @LastModifiedDate
    @NotNull
    private LocalDateTime modifiedDate;

}
