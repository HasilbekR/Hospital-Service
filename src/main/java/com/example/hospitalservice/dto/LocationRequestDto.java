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
public class LocationRequestDto {
    @NotBlank(message = "Line must not be blank!")
    private Double latitude;
    @NotBlank(message = "Line must not be blank!")
    private Double longitude;
}
