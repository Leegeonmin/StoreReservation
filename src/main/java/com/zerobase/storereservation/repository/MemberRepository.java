package com.zerobase.storereservation.repository;

import com.zerobase.storereservation.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByName(String name);
    Optional<MemberEntity> findByName(String name);
}
