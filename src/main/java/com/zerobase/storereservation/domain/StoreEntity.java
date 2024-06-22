package com.zerobase.storereservation.domain;

import com.zerobase.storereservation.dto.StoreDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "Store")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class StoreEntity {
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
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
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public void update( StoreDto dto){
        if(dto.getName() != null){
            this.name = dto.getName();
        }
        if(dto.getAddress() != null){
            this.address = dto.getAddress();
        }
        if(dto.getDescription() != null){
            this.description = dto.getDescription();
        }
    }
}
