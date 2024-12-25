package ru.costa.TOURS_APP_VUE_JWT.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "passports")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passport extends BaseEntity {

    @Column(name = "series")
    private String series;
    @Column(name = "number")
    private String number;
}
