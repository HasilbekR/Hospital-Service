package com.example.hospitalservice.Entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity(name = "workingHours")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkingHours extends BaseEntity{
    private String dayOfWeek;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
