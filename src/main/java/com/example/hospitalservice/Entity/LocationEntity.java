package com.example.hospitalservice.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "location")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    private Double latitude;
    @Column(unique = true, nullable = false)
    private Double longitude;
}
