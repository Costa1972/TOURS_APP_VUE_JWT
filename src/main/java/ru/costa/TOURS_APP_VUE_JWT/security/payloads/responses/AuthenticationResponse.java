package ru.costa.TOURS_APP_VUE_JWT.security.payloads.responses;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String tokenType;
    @NotNull
    private String token;
    private String username;
}
