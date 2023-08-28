package com.example.hospitalservice.dto;

import com.example.hospitalservice.Entity.HospitalEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class HospitalData {
    private List<HospitalEntity> hospitals;
    private List<String> cities;
}
