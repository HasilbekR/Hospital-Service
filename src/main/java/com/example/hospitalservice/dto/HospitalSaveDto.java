package com.example.hospitalservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "Address must not be blank!")
    private String address;
    @NotBlank(message = "Phone number must not be blank!")
    @Pattern(regexp = "^\\+998(90|91|92|93|94|95|97|98|99)[0-9]{7}$\n")
    private String phoneNumber;
    private LocationRequestDto location;
}
