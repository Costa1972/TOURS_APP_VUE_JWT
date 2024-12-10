package ru.costa.TOURS_APP_VUE_JWT.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.costa.TOURS_APP_VUE_JWT.models.Phone;
import ru.costa.TOURS_APP_VUE_JWT.security.payloads.responses.MessageResponse;
import ru.costa.TOURS_APP_VUE_JWT.services.PassportService;
import ru.costa.TOURS_APP_VUE_JWT.services.PaymentService;
import ru.costa.TOURS_APP_VUE_JWT.services.PhoneService;

@RestController
@RequestMapping("/api/ auth/")
@RequiredArgsConstructor
public class RequisiteController {

    private final PhoneService phoneService;
    private final PassportService passportService;
    private final PaymentService paymentService;

    @PostMapping("/addPhone")
    public ResponseEntity<?> addPhone(@RequestBody Phone phone) {
        if (phoneService.isPhoneExist(phone.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Phone already exists");
        }
        phoneService.save(phone);
        return ResponseEntity.ok(new MessageResponse("Phone added successfully"));
    }
}
