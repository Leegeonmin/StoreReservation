package com.zerobase.storereservation.domain;

import com.zerobase.storereservation.type.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "Member")
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(length = 100)
    private UserRole userRole;

    @Column(length = 100)
    @NotNull
    private String name;
    @NotNull
    private String password;
    @Column(length = 11)
    @NotNull
    private String phone;


    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;
    @LastModifiedDate
    @NotNull
    private LocalDateTime modifiedDate;
}
