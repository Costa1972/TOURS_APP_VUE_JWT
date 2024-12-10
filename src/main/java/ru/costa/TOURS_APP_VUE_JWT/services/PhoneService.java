package ru.costa.TOURS_APP_VUE_JWT.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.costa.TOURS_APP_VUE_JWT.models.Phone;
import ru.costa.TOURS_APP_VUE_JWT.repository.PhoneRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public Phone save(@RequestBody Phone phone) {
        return phoneRepository.save(phone);
    }

    public List<Phone> findAllPhones() {
        return phoneRepository.findAll();
    }

    public void deletePhone(Phone phone) {
        phoneRepository.delete(phone);
    }

    public boolean isPhoneExist(String phoneNumber) {
        return phoneRepository.existsByPhoneNumber(phoneNumber);
    }
}
