package ru.costa.TOURS_APP_VUE_JWT.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.costa.TOURS_APP_VUE_JWT.models.User;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests.SignInRequest;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests.SignUpRequest;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.responses.AuthenticationResponse;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.responses.MessageResponse;
import ru.costa.TOURS_APP_VUE_JWT.security.utils.CookieUtil;
import ru.costa.TOURS_APP_VUE_JWT.security.utils.JwtUtil;
import ru.costa.TOURS_APP_VUE_JWT.services.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private static final String BEARER_PREFIX = "Bearer ";
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse signInResponse(HttpServletResponse response, SignInRequest signInRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUsername(),
                    signInRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var user = userService.loadUserByUsername(signInRequest.getUsername());
            Set<GrantedAuthority> roles = user.getAuthorities().stream().map(role ->
                    new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toSet());
            String token = jwtUtil.generateToken(user);
            //        response.addHeader("Authorization", BEARER_PREFIX + token);
            cookieUtil.setAccessToken(response, token, 604800);
            return new AuthenticationResponse(BEARER_PREFIX, token, signInRequest.getUsername(), roles);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new AuthenticationResponse(BEARER_PREFIX, null, signInRequest.getUsername(), Set.of());
        }
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
        user.setPassport(signUpRequest.getPassport());
        user.setPhones(signUpRequest.getPhones());
        user.setPayments(signUpRequest.getPayments());
        userService.save(user);
        return ResponseEntity.ok(new MessageResponse("User created successfully"));
    }
}
