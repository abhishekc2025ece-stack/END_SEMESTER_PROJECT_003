package com.messmate.controller;

import com.messmate.entity.ResidentEntity;
import com.messmate.service.ResidentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService service;

    public ResidentController(
            ResidentService service) {

        this.service = service;
    }

    @GetMapping
    public List<ResidentEntity> getAllResidents() {
        return service.getAllResidents();
    }

    @GetMapping("/{id}")
    public ResidentEntity getResidentById(
            @PathVariable Long id) {

        return service.getResidentById(id);
    }

    @PostMapping
    public ResidentEntity createResident(
            @Valid @RequestBody ResidentEntity resident) {

        return service.createResident(resident);
    }

    @PutMapping("/{id}")
    public ResidentEntity updateResident(
            @PathVariable Long id,
            @Valid @RequestBody ResidentEntity resident) {

        return service.updateResident(id, resident);
    }

    @DeleteMapping("/{id}")
    public String deleteResident(
            @PathVariable Long id) {

        service.deleteResident(id);

        return "Resident deleted successfully";
    }
}