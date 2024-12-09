package ru.costa.TOURS_APP_VUE_JWT.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.costa.TOURS_APP_VUE_JWT.security.AuthService;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests.SignInRequest;
import ru.costa.TOURS_APP_VUE_JWT.services.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(
                authService.signInResponse(signInRequest)
        );
    }
}
