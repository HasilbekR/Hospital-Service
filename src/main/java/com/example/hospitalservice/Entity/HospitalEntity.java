package com.example.hospitalservice.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;


@Entity(name = "hospital")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HospitalEntity extends BaseEntity {
    private String name;
    private String address;
    private String location;
    private Boolean isPrivate;
    @Enumerated(EnumType.STRING)
    private HospitalStatus status;
}
