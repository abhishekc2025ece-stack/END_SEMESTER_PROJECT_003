package com.messmate.controller;

import com.messmate.entity.MealOptOutEntity;
import com.messmate.service.MealOptOutService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opt-outs")
public class MealOptOutController {

    private final MealOptOutService service;

    public MealOptOutController(
            MealOptOutService service) {

        this.service = service;
    }

    @PostMapping("/resident/{residentId}/meal/{mealId}")
    public MealOptOutEntity optOut(
            @PathVariable Long residentId,
            @PathVariable Long mealId) {

        return service.optOut(
                residentId,
                mealId);
    }

    @GetMapping("/meal/{mealId}")
    public List<MealOptOutEntity> getOptOutsForMeal(
            @PathVariable Long mealId) {

        return service.getOptOutsForMeal(mealId);
    }

    @GetMapping("/resident/{residentId}")
    public List<MealOptOutEntity> getOptOutsForResident(
            @PathVariable Long residentId) {

        return service.getOptOutsForResident(
                residentId);
    }

    @GetMapping("/meal/{mealId}/count")
    public long getOptOutCount(
            @PathVariable Long mealId) {

        return service.getOptOutCount(mealId);
    }

    @GetMapping("/meal/{mealId}/headcount")
    public long getMealHeadcount(
            @PathVariable Long mealId) {

        return service.getMealHeadcount(mealId);
    }

    @DeleteMapping("/{id}")
    public String cancelOptOut(
            @PathVariable Long id) {

        service.cancelOptOut(id);

        return "Meal opt-out cancelled successfully";
    }
}