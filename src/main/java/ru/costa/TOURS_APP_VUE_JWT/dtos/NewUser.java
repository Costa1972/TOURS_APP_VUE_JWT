package ru.costa.TOURS_APP_VUE_JWT.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.costa.TOURS_APP_VUE_JWT.models.Passport;
import ru.costa.TOURS_APP_VUE_JWT.models.Payment;
import ru.costa.TOURS_APP_VUE_JWT.models.Phone;
import ru.costa.TOURS_APP_VUE_JWT.models.Role;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUser {

    private String username;
    private String password;
    private String confirmPassword;
    private String lastName;
    private String firstName;
    private String patronymic;
    private Set<Phone> phones;
    private Set<Role> roles;
    private Set<Payment> payments;
    private Passport passport;
}
