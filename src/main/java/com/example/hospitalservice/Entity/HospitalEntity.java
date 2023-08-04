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
    @Column(unique = true, nullable = false)
    private String address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hospital_id",referencedColumnName = "id")
    private List<WorkingHours> workingHours;
    @Enumerated(EnumType.STRING)
    private HospitalStatus status;
}
