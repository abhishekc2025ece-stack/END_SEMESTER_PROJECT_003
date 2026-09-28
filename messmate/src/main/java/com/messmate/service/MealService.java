package com.messmate.service;

import com.messmate.entity.MealEntity;
import com.messmate.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    // ==============================
    // GET ALL MEALS
    // ==============================

    public List<MealEntity> getAllMeals() {

        return repository.findAll();
    }

    // ==============================
    // GET MEAL BY ID
    // ==============================

    public MealEntity getMealById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Meal not found"
                        )
                );
    }

    // ==============================
    // CREATE MEAL
    // ==============================

    public MealEntity createMeal(MealEntity meal) {

        if (meal.getDate() == null) {

            throw new RuntimeException(
                    "Meal date is required"
            );
        }

        if (meal.getMealType() == null ||
                meal.getMealType().trim().isEmpty()) {

            throw new RuntimeException(
                    "Meal type is required"
            );
        }

        if (meal.getMenuItem() == null ||
                meal.getMenuItem().getId() == null) {

            throw new RuntimeException(
                    "Menu item is required"
            );
        }

        return repository.save(meal);
    }

    // ==============================
    // UPDATE MEAL
    // ==============================

    public MealEntity updateMeal(
            Long id,
            MealEntity meal) {

        MealEntity existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Meal not found"
                                )
                        );

        if (meal.getDate() == null) {

            throw new RuntimeException(
                    "Meal date is required"
            );
        }

        if (meal.getMealType() == null ||
                meal.getMealType().trim().isEmpty()) {

            throw new RuntimeException(
                    "Meal type is required"
            );
        }

        if (meal.getMenuItem() == null ||
                meal.getMenuItem().getId() == null) {

            throw new RuntimeException(
                    "Menu item is required"
            );
        }

        existing.setDate(meal.getDate());
        existing.setMealType(meal.getMealType());
        existing.setMenuItem(meal.getMenuItem());

        return repository.save(existing);
    }

    // ==============================
    // DELETE MEAL
    // ==============================

    public void deleteMeal(Long id) {

        if (!repository.existsById(id)) {

            throw new RuntimeException(
                    "Meal not found"
            );
        }

        repository.deleteById(id);
    }

    // ==============================
    // WEEKLY MEALS
    // ==============================

    public List<MealEntity> getWeeklyMeals(
            LocalDate startDate) {

        if (startDate == null) {

            throw new RuntimeException(
                    "Start date is required"
            );
        }

        LocalDate endDate =
                startDate.plusDays(6);

        return repository
                .findByDateBetweenOrderByDateAscMealTypeAsc(
                        startDate,
                        endDate
                );
    }
}