package com.example.hospitalservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class HospitalInfo {
    private UUID id;
    private String name;
    private String city;
}
