package com.messmate.service;

import com.messmate.entity.ResidentEntity;
import com.messmate.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentService {

    private final ResidentRepository repository;

    public ResidentService(ResidentRepository repository) {
        this.repository = repository;
    }

    public List<ResidentEntity> getAllResidents() {
        return repository.findAll();
    }

    public ResidentEntity getResidentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Resident not found"));
    }

    public ResidentEntity createResident(ResidentEntity resident) {
        return repository.save(resident);
    }

    public ResidentEntity updateResident(
            Long id,
            ResidentEntity resident) {

        ResidentEntity existing =
                getResidentById(id);

        existing.setName(resident.getName());
        existing.setEmail(resident.getEmail());
        existing.setPassword(resident.getPassword());
        existing.setRole(resident.getRole());

        return repository.save(existing);
    }

    public void deleteResident(Long id) {
        repository.deleteById(id);
    }

    public ResidentEntity getResidentByEmail(String email) {

        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Resident not found"));
    }
}