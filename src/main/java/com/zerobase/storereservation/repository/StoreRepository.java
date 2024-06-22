package com.zerobase.storereservation.repository;

import com.zerobase.storereservation.domain.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    Optional<StoreEntity> findByMemberId(Long id);
}
