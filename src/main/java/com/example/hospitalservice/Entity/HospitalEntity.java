package com.example.hospitalservice.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity(name = "hospital")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HospitalEntity extends BaseEntity {
    private String name;
    private String city;
    @Column(unique = true, nullable = false)
    private String address;
    @OneToOne(cascade = CascadeType.ALL)
    private LocationEntity location;
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<WorkingHoursEntity> workingHours;
    @Enumerated(EnumType.STRING)
    private HospitalStatus status;
}
