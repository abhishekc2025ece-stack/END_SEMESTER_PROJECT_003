package com.messmate.repository;

import com.messmate.entity.ResidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResidentRepository
        extends JpaRepository<ResidentEntity, Long> {

    Optional<ResidentEntity> findByEmail(String email);
}