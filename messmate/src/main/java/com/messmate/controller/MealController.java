package com.messmate.controller;

import com.messmate.entity.MealEntity;
import com.messmate.service.MealService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService service;

    public MealController(MealService service) {
        this.service = service;
    }

    // ==============================
    // GET ALL MEALS
    // ==============================

    @GetMapping
    public List<MealEntity> getAllMeals() {

        return service.getAllMeals();
    }

    // ==============================
    // GET WEEKLY MEALS
    // ==============================

    @GetMapping("/week")
    public List<MealEntity> getWeeklyMeals(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate) {

        return service.getWeeklyMeals(startDate);
    }

    // ==============================
    // GET MEAL BY ID
    // ==============================

    @GetMapping("/{id}")
    public MealEntity getMealById(
            @PathVariable Long id) {

        return service.getMealById(id);
    }

    // ==============================
    // CREATE MEAL
    // ==============================

    @PostMapping
    public MealEntity createMeal(
            @Valid @RequestBody MealEntity meal) {

        return service.createMeal(meal);
    }

    // ==============================
    // UPDATE MEAL
    // ==============================

    @PutMapping("/{id}")
    public MealEntity updateMeal(
            @PathVariable Long id,
            @Valid @RequestBody MealEntity meal) {

        return service.updateMeal(id, meal);
    }

    // ==============================
    // DELETE MEAL
    // ==============================

    @DeleteMapping("/{id}")
    public String deleteMeal(
            @PathVariable Long id) {

        service.deleteMeal(id);

        return "Meal deleted successfully";
    }
}