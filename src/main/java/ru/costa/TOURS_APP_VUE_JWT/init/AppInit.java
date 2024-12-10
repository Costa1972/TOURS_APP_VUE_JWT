package ru.costa.TOURS_APP_VUE_JWT.init;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.costa.TOURS_APP_VUE_JWT.models.*;
import ru.costa.TOURS_APP_VUE_JWT.repository.*;

import java.util.HashSet;
import java.util.Set;

@Component
public class AppInit {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PassportRepository passportRepository;
    private final PhoneRepository phoneRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaymentRepository paymentRepository;

    public AppInit(UserRepository userRepository,
                   RoleRepository roleRepository,
                   PassportRepository passportRepository,
                   PhoneRepository phoneRepository,
                   PasswordEncoder passwordEncoder, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passportRepository = passportRepository;
        this.phoneRepository = phoneRepository;
        this.passwordEncoder = passwordEncoder;

        Role admin = checkRole("ROLE_ADMIN", roleRepository);
        Role user = checkRole("ROLE_USER", roleRepository);
        Set<Role> roles = new HashSet<>();
        roles.add(admin);
        roles.add(user);

        Passport passport1 = Passport.builder()
                .series("3476")
                .number("740938")
                .build();
        Passport passport2 = Passport.builder()
                .series("4523")
                .number("973476")
                .build();

        Phone phoneUser1 = Phone.builder()
                .phoneNumber("+7 495 392-12-93")
                .phoneType("work: ")
                .build();
        Phone mobPhoneUser1 = Phone.builder()
                .phoneNumber("+7 916 675-98-93")
                .phoneType("mobile: ")
                .build();
        Phone phoneUser2 = Phone.builder()
                .phoneNumber("+7 495 274-09-23")
                .phoneType("work: ")
                .build();
        Phone mobPhoneUser2 = Phone.builder()
                .phoneNumber("+7 910 705-21-73")
                .phoneType("mobile: ")
                .build();
        phoneRepository.save(phoneUser1);
        phoneRepository.save(mobPhoneUser1);
        phoneRepository.save(phoneUser2);
        phoneRepository.save(mobPhoneUser2);

        Payment payment1 = new Payment(0L, 300.00, BasisOfPayment.MEAL);
        Payment payment2 = new Payment(0L, 100.00, BasisOfPayment.TRAIN);
        Payment payment3 = new Payment(0L, 500.00, BasisOfPayment.MUSICIANS);
        Payment payment4 = new Payment(0L, 500.00, BasisOfPayment.BUS);
        Set<Payment> payments = new HashSet<>();
        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);
        payments.add(payment4);

        User user1 = User.builder()
                .username("anton@mail.ru")
                .password(passwordEncoder.encode("anton"))
                .lastName("Antonov")
                .firstName("Anton")
                .patronymic("Antonovich")
                .passport(passport1)
//                .phones(Set.of(phoneUser1, mobPhoneUser2))
//                .payments(Set.of(payment2, payment4))
                .roles(Set.of(admin, user))
                .build();
        User user2 = User.builder()
                .username("oleg@mail.ru")
                .password(passwordEncoder.encode("oleg"))
                .lastName("Smirnov")
                .firstName("Oleg")
                .patronymic("Ivanovich")
                .passport(passport2)
//                .phones(Set.of(phoneUser2, mobPhoneUser1))
//                .payments(Set.of(payment1, payment3))
                .roles(Set.of(user))
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        this.paymentRepository = paymentRepository;
    }

    public Role checkRole(String name, RoleRepository roleRepository) {
        return roleRepository.findByName(name).
                orElseGet(() -> roleRepository.save(Role.builder().name(name).build()));
    }
}
