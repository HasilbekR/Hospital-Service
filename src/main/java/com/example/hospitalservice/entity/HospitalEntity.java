package com.example.hospitalservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
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
    private Integer number;
    private List<String> doctors;
    private String address;
    private String location;
    private Boolean isPrivate;
    @Enumerated(EnumType.STRING)
    private HospitalStatus status;


}
