package ru.costa.TOURS_APP_VUE_JWT.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.costa.TOURS_APP_VUE_JWT.models.User;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests.SignInRequest;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests.SignUpRequest;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.responses.AuthenticationResponse;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.responses.MessageResponse;
import ru.costa.TOURS_APP_VUE_JWT.security.utils.JwtUtil;
import ru.costa.TOURS_APP_VUE_JWT.services.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String BEARER_PREFIX = "Bearer ";
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse signInResponse(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(signInRequest.getUsername());
        String token = jwtUtil.getAuthenticationToken(userDetails);
        return new AuthenticationResponse(BEARER_PREFIX, token);
    }

    public ResponseEntity<?> signUpResponse(SignUpRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                signUpRequest.getConfirmPassword(),
                signUpRequest.getLastName(),
                signUpRequest.getFirstName(),
                signUpRequest.getPatronymic());
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username or password cannot be empty"));
        }
        if (userService.ifUserExists(username)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username already exists"));
        }
        if (!password.equals(signUpRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Passwords do not match"));
        }
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);
        String token = jwtUtil.getAuthenticationToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(BEARER_PREFIX, token));
    }
}
