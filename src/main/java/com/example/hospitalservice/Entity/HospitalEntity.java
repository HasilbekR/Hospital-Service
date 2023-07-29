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
    @Enumerated(EnumType.STRING)
    private HospitalStatus status;
}
