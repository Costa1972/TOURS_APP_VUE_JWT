package ru.costa.TOURS_APP_VUE_JWT.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.costa.TOURS_APP_VUE_JWT.models.Role;
import ru.costa.TOURS_APP_VUE_JWT.repository.RoleRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> getRole(String name) {
        return roleRepository.findByName(name);
    }

    public boolean isRoleExist(String name) {
        return roleRepository.existsByName(name);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }
}
