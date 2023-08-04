package com.example.hospitalservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkingHoursCreateDto {
    private String dayOfWeek;
    private String openingTime;
    private String closingTime;
}
