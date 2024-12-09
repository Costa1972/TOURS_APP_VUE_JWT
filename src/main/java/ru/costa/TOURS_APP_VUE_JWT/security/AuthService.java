package ru.costa.TOURS_APP_VUE_JWT.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests.SignInRequest;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.responses.AuthenticationResponse;
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
}
