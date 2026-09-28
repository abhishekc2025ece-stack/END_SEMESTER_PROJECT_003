package com.messmate.repository;

import com.messmate.entity.MealOptOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealOptOutRepository
        extends JpaRepository<MealOptOutEntity, Long> {

    boolean existsByResidentIdAndMealId(
            Long residentId,
            Long mealId
    );

    List<MealOptOutEntity> findByMealId(Long mealId);

    List<MealOptOutEntity> findByResidentId(Long residentId);

    long countByMealId(Long mealId);
}