package com.messmate.repository;

import com.messmate.entity.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<MealEntity, Long> {

    List<MealEntity> findByDateBetweenOrderByDateAscMealTypeAsc(
            LocalDate startDate,
            LocalDate endDate
    );

    List<MealEntity> findByMenuItemId(Long menuItemId);

    boolean existsByMenuItemId(Long menuItemId);

    List<MealEntity> findByDate(LocalDate date);
}