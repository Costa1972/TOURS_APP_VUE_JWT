package ru.costa.TOURS_APP_VUE_JWT.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity{

    private Double amount;
    private BasisOfPayment basisOfPayment;

    public Payment(double amount, BasisOfPayment basisOfPayment) {
        this.amount = amount;
        this.basisOfPayment = basisOfPayment;
    }
}
