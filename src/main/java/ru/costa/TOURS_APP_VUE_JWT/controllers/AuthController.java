package ru.costa.TOURS_APP_VUE_JWT.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.costa.TOURS_APP_VUE_JWT.security.AuthService;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests.SignInRequest;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests.SignUpRequest;
import ru.costa.TOURS_APP_VUE_JWT.security.utils.JwtUtil;
import ru.costa.TOURS_APP_VUE_JWT.services.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/signIn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(
                authService.signInResponse(signInRequest)
        );
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUpResponse(signUpRequest);
    }
}
