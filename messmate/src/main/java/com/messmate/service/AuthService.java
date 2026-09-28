package com.messmate.service;

import com.messmate.entity.ResidentEntity;
import com.messmate.repository.ResidentRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ResidentRepository repository;

    public AuthService(
            ResidentRepository repository) {

        this.repository = repository;
    }

    public ResidentEntity login(
            String email,
            String password) {

        ResidentEntity resident =
                repository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid email or password"));

        if (!resident.getPassword()
                .equals(password)) {

            throw new RuntimeException(
                    "Invalid email or password");
        }

        return resident;
    }
}