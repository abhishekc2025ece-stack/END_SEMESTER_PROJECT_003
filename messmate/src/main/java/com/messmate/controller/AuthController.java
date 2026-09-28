
package com.messmate.controller;

import com.messmate.dto.LoginRequest;
import com.messmate.dto.LoginResponse;
import com.messmate.entity.ResidentEntity;
import com.messmate.repository.ResidentRepository;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ResidentRepository repository;

    public AuthController(ResidentRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        ResidentEntity resident =
                repository.findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException("Invalid email or password"));

        if (!resident.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return new LoginResponse(
                resident.getId(),
                resident.getName(),
                resident.getEmail(),
                resident.getRole()
        );
    }
}