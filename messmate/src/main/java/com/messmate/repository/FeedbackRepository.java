package com.messmate.repository;

import com.messmate.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {

    // Get feedback for a particular meal
    List<FeedbackEntity> findByMealId(Long mealId);

    // Get feedback given by a particular resident
    List<FeedbackEntity> findByResidentId(Long residentId);

    // Check duplicate feedback
    boolean existsByResidentIdAndMealId(Long residentId, Long mealId);

    // Average rating for one meal
    @Query("""
            SELECT AVG(f.rating)
            FROM FeedbackEntity f
            WHERE f.meal.id = :mealId
            """)
    Double getAverageRatingByMeal(@Param("mealId") Long mealId);

    // Average rating for a particular date
    @Query("""
            SELECT AVG(f.rating)
            FROM FeedbackEntity f
            WHERE f.meal.date = :date
            """)
    Double getAverageRatingByDay(@Param("date") LocalDate date);

    // Find meals with average rating below threshold
    @Query("""
            SELECT f.meal.id,
                   f.meal.date,
                   f.meal.mealType,
                   AVG(f.rating)
            FROM FeedbackEntity f
            GROUP BY f.meal.id,
                     f.meal.date,
                     f.meal.mealType
            HAVING AVG(f.rating) < :threshold
            ORDER BY AVG(f.rating) ASC
            """)
    List<Object[]> findLowRatedMeals(
            @Param("threshold") double threshold
    );
}