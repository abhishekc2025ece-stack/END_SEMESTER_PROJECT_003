package com.messmate.service;

import com.messmate.entity.FeedbackEntity;
import com.messmate.entity.MealEntity;
import com.messmate.entity.ResidentEntity;
import com.messmate.repository.FeedbackRepository;
import com.messmate.repository.MealRepository;
import com.messmate.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ResidentRepository residentRepository;
    private final MealRepository mealRepository;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            ResidentRepository residentRepository,
            MealRepository mealRepository) {

        this.feedbackRepository = feedbackRepository;
        this.residentRepository = residentRepository;
        this.mealRepository = mealRepository;
    }

    // ==============================
    // CREATE FEEDBACK
    // ==============================

    public FeedbackEntity createFeedback(FeedbackEntity feedback) {

        if (feedback.getResident() == null ||
                feedback.getResident().getId() == null) {

            throw new RuntimeException("Resident is required");
        }

        if (feedback.getMeal() == null ||
                feedback.getMeal().getId() == null) {

            throw new RuntimeException("Meal is required");
        }

        if (feedback.getRating() == null ||
                feedback.getRating() < 1 ||
                feedback.getRating() > 5) {

            throw new RuntimeException(
                    "Rating must be between 1 and 5"
            );
        }

        Long residentId = feedback.getResident().getId();
        Long mealId = feedback.getMeal().getId();

        // Check resident
        ResidentEntity resident =
                residentRepository.findById(residentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Resident not found"
                                )
                        );

        // Check meal
        MealEntity meal =
                mealRepository.findById(mealId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Meal not found"
                                )
                        );

        // Prevent duplicate feedback
        if (feedbackRepository.existsByResidentIdAndMealId(
                residentId,
                mealId)) {

            throw new RuntimeException(
                    "Resident has already submitted feedback for this meal"
            );
        }

        // Set actual database entities
        feedback.setResident(resident);
        feedback.setMeal(meal);

        return feedbackRepository.save(feedback);
    }

    // ==============================
    // GET ALL FEEDBACK
    // ==============================

    public List<FeedbackEntity> getAllFeedback() {

        return feedbackRepository.findAll();
    }

    // ==============================
    // GET FEEDBACK BY ID
    // ==============================

    public FeedbackEntity getFeedbackById(Long id) {

        return feedbackRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Feedback not found"
                        )
                );
    }

    // ==============================
    // UPDATE FEEDBACK
    // ==============================

    public FeedbackEntity updateFeedback(
            Long id,
            FeedbackEntity feedback) {

        FeedbackEntity existing =
                feedbackRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Feedback not found"
                                )
                        );

        if (feedback.getRating() == null ||
                feedback.getRating() < 1 ||
                feedback.getRating() > 5) {

            throw new RuntimeException(
                    "Rating must be between 1 and 5"
            );
        }

        existing.setRating(feedback.getRating());
        existing.setComment(feedback.getComment());

        return feedbackRepository.save(existing);
    }

    // ==============================
    // DELETE FEEDBACK
    // ==============================

    public void deleteFeedback(Long id) {

        if (!feedbackRepository.existsById(id)) {

            throw new RuntimeException(
                    "Feedback not found"
            );
        }

        feedbackRepository.deleteById(id);
    }

    // ==============================
    // GET FEEDBACK BY MEAL
    // ==============================

    public List<FeedbackEntity> getFeedbackByMeal(
            Long mealId) {

        if (!mealRepository.existsById(mealId)) {

            throw new RuntimeException(
                    "Meal not found"
            );
        }

        return feedbackRepository.findByMealId(mealId);
    }

    // ==============================
    // GET FEEDBACK BY RESIDENT
    // ==============================

    public List<FeedbackEntity> getFeedbackByResident(
            Long residentId) {

        if (!residentRepository.existsById(residentId)) {

            throw new RuntimeException(
                    "Resident not found"
            );
        }

        return feedbackRepository.findByResidentId(residentId);
    }

    // ==============================
    // AVERAGE RATING FOR MEAL
    // ==============================

    public Double getAverageRatingByMeal(Long mealId) {

        if (!mealRepository.existsById(mealId)) {

            throw new RuntimeException(
                    "Meal not found"
            );
        }

        Double average =
                feedbackRepository.getAverageRatingByMeal(mealId);

        if (average == null) {
            return 0.0;
        }

        return Math.round(average * 100.0) / 100.0;
    }

    // ==============================
    // AVERAGE RATING FOR DAY
    // ==============================

    public Double getAverageRatingByDay(LocalDate date) {

        Double average =
                feedbackRepository.getAverageRatingByDay(date);

        if (average == null) {
            return 0.0;
        }

        return Math.round(average * 100.0) / 100.0;
    }

    // ==============================
    // LOW RATED MEALS
    // ==============================

    public List<Object[]> getLowRatedMeals(
            double threshold) {

        if (threshold < 1 || threshold > 5) {

            throw new RuntimeException(
                    "Threshold must be between 1 and 5"
            );
        }

        return feedbackRepository.findLowRatedMeals(
                threshold
        );
    }
}