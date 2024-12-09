package ru.costa.TOURS_APP_VUE_JWT.init;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.costa.TOURS_APP_VUE_JWT.models.Role;
import ru.costa.TOURS_APP_VUE_JWT.models.User;
import ru.costa.TOURS_APP_VUE_JWT.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class AppInit {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        Role admin = Role.builder()
                .name("ROLE_ADMIN")
                .build();
        Role user = Role.builder()
                .name("ROLE_USER")
                .build();
        Set<Role> roles = new HashSet<>();
        roles.add(admin);
        roles.add(user);

        User user1 = User.builder()
                .username("anton@mail.ru")
                .password(passwordEncoder.encode("anton"))
                .lastName("Antonov")
                .firstName("Anton")
                .patronymic("Antonovich")
                .phone("+ 7 977 456-78-98")
                .roles(Set.of(admin, user))
                .build();
        userRepository.save(user1);
    }
}
