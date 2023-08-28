package com.example.hospitalservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkingHoursCreateDto {
    @NotBlank(message = "Line must not be blank!")
    private String dayOfWeek;
    @NotBlank(message = "Line must not be blank!")
    private String openingTime;
    @NotBlank(message = "Line must not be blank!")
    private String closingTime;
}
