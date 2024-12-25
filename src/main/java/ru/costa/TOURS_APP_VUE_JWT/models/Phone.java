package ru.costa.TOURS_APP_VUE_JWT.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone extends BaseEntity{

    private String phoneNumber;
    private String phoneType;
}
