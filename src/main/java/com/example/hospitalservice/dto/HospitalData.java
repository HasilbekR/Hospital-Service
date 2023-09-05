package com.example.hospitalservice.dto;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HospitalData {
    private List<HospitalInfo> hospitals;
    private List<String> cities;
    private Integer pagesCount;
}
