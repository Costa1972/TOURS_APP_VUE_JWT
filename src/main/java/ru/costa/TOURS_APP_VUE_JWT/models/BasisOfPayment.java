package ru.costa.TOURS_APP_VUE_JWT.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BasisOfPayment {
    BUS("bop1"),
    TRAIN("pop2"),
    MEAL("bop3"),
    MUSICIANS("bop4");

    private String codeOfBasis;

    public String getCodeOfBasis() {
        return codeOfBasis;
    }

    public void setCodeOfBasis(String codeOfBasis) {
        this.codeOfBasis = codeOfBasis;
    }

    @JsonCreator
    public static BasisOfPayment codeBop(String value) {
        for (BasisOfPayment basis : BasisOfPayment.values()) {
            if (basis.name().equals(value)) {
                return basis;
            }
        }
        return null;
    }
}
