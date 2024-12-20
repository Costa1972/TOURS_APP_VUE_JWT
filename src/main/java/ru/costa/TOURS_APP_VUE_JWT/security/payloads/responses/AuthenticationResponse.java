package ru.costa.TOURS_APP_VUE_JWT.security.payloads.responses;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String bearerPrefix;
    private String tokenType;
    @NotNull
    private String token;
    private String username;
    private Set<GrantedAuthority> authorities;

    public AuthenticationResponse(String bearerPrefix,
                                  String token,
                                  String username,
                                  Set<GrantedAuthority> roles) {
        this.bearerPrefix = bearerPrefix;
        this.token = token;
        this.username = username;
        this.authorities = roles;
    }
}
