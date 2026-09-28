package com.messmate.service;

import com.messmate.entity.MealEntity;
import com.messmate.entity.MealOptOutEntity;
import com.messmate.entity.ResidentEntity;
import com.messmate.repository.MealOptOutRepository;
import com.messmate.repository.MealRepository;
import com.messmate.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealOptOutService {

    private final MealOptOutRepository optOutRepository;
    private final ResidentRepository residentRepository;
    private final MealRepository mealRepository;

    public MealOptOutService(
            MealOptOutRepository optOutRepository,
            ResidentRepository residentRepository,
            MealRepository mealRepository) {

        this.optOutRepository = optOutRepository;
        this.residentRepository = residentRepository;
        this.mealRepository = mealRepository;
    }

    public MealOptOutEntity optOut(
            Long residentId,
            Long mealId) {

        ResidentEntity resident =
                residentRepository.findById(residentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Resident not found"));

        MealEntity meal =
                mealRepository.findById(mealId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Meal not found"));

        if (optOutRepository
                .existsByResidentIdAndMealId(
                        residentId,
                        mealId)) {

            throw new IllegalArgumentException(
                    "Resident has already opted out of this meal");
        }

        MealOptOutEntity optOut =
                new MealOptOutEntity(
                        resident,
                        meal);

        return optOutRepository.save(optOut);
    }

    public List<MealOptOutEntity> getOptOutsForMeal(
            Long mealId) {

        return optOutRepository.findByMealId(mealId);
    }

    public List<MealOptOutEntity> getOptOutsForResident(
            Long residentId) {

        return optOutRepository.findByResidentId(
                residentId);
    }

    public long getOptOutCount(Long mealId) {

        return optOutRepository.countByMealId(
                mealId);
    }

    public long getMealHeadcount(Long mealId) {

        long totalResidents =
                residentRepository.count();

        long optedOut =
                optOutRepository.countByMealId(
                        mealId);

        return totalResidents - optedOut;
    }

    public void cancelOptOut(Long id) {

        if (!optOutRepository.existsById(id)) {

            throw new RuntimeException(
                    "Opt-out record not found");
        }

        optOutRepository.deleteById(id);
    }
}