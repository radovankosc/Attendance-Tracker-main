package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.dto.UserDto;
import com.ctrlaltelite.backend.services.RegistrationService;
import com.ctrlaltelite.backend.exceptions.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto user) {
        System.out.println(user);
            registrationService.register(user);
            return ResponseEntity.ok().build();
}

    @GetMapping("/confirm/{activationCode}")
    public ResponseEntity<String> activate(@RequestBody String username, @PathVariable String activationCode) {
            registrationService.activate(username, activationCode);
            return ResponseEntity.ok().build();
    }
}

