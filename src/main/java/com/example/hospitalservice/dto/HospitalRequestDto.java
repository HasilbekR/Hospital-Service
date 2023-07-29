package com.example.hospitalservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HospitalRequestDto {
    private String name;
    private String number;
    private String address;
    private String location;
    private boolean isPrivate;
}
