package com.messmate.controller;

import com.messmate.entity.FeedbackEntity;
import com.messmate.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    // ==========================================
    // GET ALL FEEDBACK
    // ==========================================

    @GetMapping
    public List<FeedbackEntity> getAllFeedback() {

        return service.getAllFeedback();
    }

    // ==========================================
    // GET FEEDBACK BY ID
    // ==========================================

    @GetMapping("/{id}")
    public FeedbackEntity getFeedbackById(
            @PathVariable Long id) {

        return service.getFeedbackById(id);
    }

    // ==========================================
    // CREATE FEEDBACK
    // ==========================================

    @PostMapping
    public FeedbackEntity createFeedback(
            @Valid @RequestBody FeedbackEntity feedback) {

        return service.createFeedback(feedback);
    }

    // ==========================================
    // UPDATE FEEDBACK
    // ==========================================

    @PutMapping("/{id}")
    public FeedbackEntity updateFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackEntity feedback) {

        return service.updateFeedback(id, feedback);
    }

    // ==========================================
    // DELETE FEEDBACK
    // ==========================================

    @DeleteMapping("/{id}")
    public String deleteFeedback(
            @PathVariable Long id) {

        service.deleteFeedback(id);

        return "Feedback deleted successfully";
    }

    // ==========================================
    // GET FEEDBACK BY MEAL
    // ==========================================

    @GetMapping("/meal/{mealId}")
    public List<FeedbackEntity> getFeedbackByMeal(
            @PathVariable Long mealId) {

        return service.getFeedbackByMeal(mealId);
    }

    // ==========================================
    // GET FEEDBACK BY RESIDENT
    // ==========================================

    @GetMapping("/resident/{residentId}")
    public List<FeedbackEntity> getFeedbackByResident(
            @PathVariable Long residentId) {

        return service.getFeedbackByResident(residentId);
    }

    // ==========================================
    // AVERAGE RATING FOR ONE MEAL
    // ==========================================

    @GetMapping("/meal/{mealId}/average")
    public Double getAverageRatingByMeal(
            @PathVariable Long mealId) {

        return service.getAverageRatingByMeal(mealId);
    }

    // ==========================================
    // AVERAGE RATING FOR ONE DAY
    // ==========================================

    @GetMapping("/day/{date}/average")
    public Double getAverageRatingByDay(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return service.getAverageRatingByDay(date);
    }

    // ==========================================
    // LOW RATED MEALS
    // ==========================================

    @GetMapping("/low-rated")
    public List<Object[]> getLowRatedMeals(
            @RequestParam(defaultValue = "3.0")
            double threshold) {

        return service.getLowRatedMeals(threshold);
    }
}