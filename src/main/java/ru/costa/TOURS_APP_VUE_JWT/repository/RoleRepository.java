package ru.costa.TOURS_APP_VUE_JWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.costa.TOURS_APP_VUE_JWT.models.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("FROM Role r WHERE r.name = :name")
    Optional<Role> findByName(@Param("name") String name);

    boolean existsByName(String name);
}
