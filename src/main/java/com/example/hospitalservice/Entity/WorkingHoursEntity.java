package com.example.hospitalservice.Entity;

import jakarta.persistence.Column;
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
public class WorkingHoursEntity extends BaseEntity{
    @Column(nullable = false)
    private String dayOfWeek;
    @Column(nullable = false)
    private String openingTime;
    @Column(nullable = false)
    private String closingTime;
}
