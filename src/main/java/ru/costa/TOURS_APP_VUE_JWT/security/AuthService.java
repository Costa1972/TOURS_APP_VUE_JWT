package ru.costa.TOURS_APP_VUE_JWT.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.costa.TOURS_APP_VUE_JWT.models.Role;
import ru.costa.TOURS_APP_VUE_JWT.models.User;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests.SignInRequest;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests.SignUpRequest;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.responses.AuthenticationResponse;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.responses.MessageResponse;
import ru.costa.TOURS_APP_VUE_JWT.security.utils.JwtUtil;
import ru.costa.TOURS_APP_VUE_JWT.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        var user = userService.loadUserByUsername(signInRequest.getUsername());
        String token = jwtUtil.generateToken(user);
        return new AuthenticationResponse(BEARER_PREFIX, token, signInRequest.getUsername());
    }

    public ResponseEntity<?> signUpResponse(SignUpRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                signUpRequest.getConfirmPassword(),
                signUpRequest.getLastName(),
                signUpRequest.getFirstName(),
                signUpRequest.getPatronymic(),
                signUpRequest.getRoles());
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
        return ResponseEntity.ok(new MessageResponse("User created successfully"));
    }
}
