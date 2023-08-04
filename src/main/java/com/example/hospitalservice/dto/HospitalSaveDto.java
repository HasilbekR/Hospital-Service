package com.example.hospitalservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HospitalSaveDto {
    private String name;
    private String address;
    private String phoneNumber;
    private LocationRequestDto location;
}
