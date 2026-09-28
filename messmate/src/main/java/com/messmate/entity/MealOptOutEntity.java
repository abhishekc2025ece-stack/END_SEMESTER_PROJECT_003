package com.messmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"resident_id", "meal_id"}
        )
)
public class MealOptOutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    @NotNull(message = "Resident is required")
    private ResidentEntity resident;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    @NotNull(message = "Meal is required")
    private MealEntity meal;

    public MealOptOutEntity() {
    }

    public MealOptOutEntity(
            ResidentEntity resident,
            MealEntity meal) {

        this.resident = resident;
        this.meal = meal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResidentEntity getResident() {
        return resident;
    }

    public void setResident(ResidentEntity resident) {
        this.resident = resident;
    }

    public MealEntity getMeal() {
        return meal;
    }

    public void setMeal(MealEntity meal) {
        this.meal = meal;
    }
}