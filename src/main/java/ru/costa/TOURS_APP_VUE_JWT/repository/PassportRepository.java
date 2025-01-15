package ru.costa.TOURS_APP_VUE_JWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.costa.TOURS_APP_VUE_JWT.models.Passport;

import java.util.Optional;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {

    @Query("FROM Passport p WHERE p.number = :nunber")
    Optional<Passport> findByNumber(@Param("number") String number);
}
