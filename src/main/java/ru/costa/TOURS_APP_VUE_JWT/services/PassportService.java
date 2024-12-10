package ru.costa.TOURS_APP_VUE_JWT.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.costa.TOURS_APP_VUE_JWT.models.Passport;
import ru.costa.TOURS_APP_VUE_JWT.repository.PassportRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PassportService {

    private final PassportRepository passportRepository;

    public Passport addPassport(Passport passport) {
        return passportRepository.save(passport);
    }
    public List<Passport> getAllPassports() {
        return passportRepository.findAll();
    }

    public void deletePassport(Passport passport) {
        passportRepository.delete(passport);
    }
}
