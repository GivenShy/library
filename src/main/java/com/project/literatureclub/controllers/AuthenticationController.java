package com.project.literatureclub.controllers;

import com.project.literatureclub.dtos.AuthenticationRequest;
import com.project.literatureclub.dtos.AuthenticationResponse;
import com.project.literatureclub.dtos.RegisterUserRequest;
import com.project.literatureclub.exceptions.IncorrectPasswordException;
import com.project.literatureclub.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterUserRequest request) throws IncorrectPasswordException {
        System.out.println("ekela");
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));

    }
}
