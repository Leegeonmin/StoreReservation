package com.zerobase.storereservation.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "Store")
@EntityListeners(AuditingEntityListener.class)
public class StoreEntity {
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String name;
    private String address;
    private String description;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
