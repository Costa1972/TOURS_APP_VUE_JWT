package ru.costa.TOURS_APP_VUE_JWT.security.payloads.requests;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.costa.TOURS_APP_VUE_JWT.models.Role;

import java.util.Set;

@Data
@AllArgsConstructor
public class SignUpRequest {

    private String lastName;
    private String firstName;
    private String patronymic;
    private String username;
    private String password;
    private String confirmPassword;
    private Set<Role> roles;
}
