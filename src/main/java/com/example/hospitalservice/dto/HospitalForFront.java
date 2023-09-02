package com.example.hospitalservice.dto;

import com.example.hospitalservice.Entity.WorkingHoursEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class HospitalForFront {
    private UUID id;
    private String name;
    private String city;
    private String address;
    private String location;
    private String phoneNumber;
    private List<WorkingHoursEntity> workingHours;

}
