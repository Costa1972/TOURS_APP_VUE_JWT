package ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;

    private Set<String> roles;

}
