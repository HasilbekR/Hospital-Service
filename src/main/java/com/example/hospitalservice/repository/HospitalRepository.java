package com.example.hospitalservice.repository;

import com.example.hospitalservice.Entity.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HospitalRepository extends JpaRepository<HospitalEntity, UUID>{

    Optional<HospitalEntity> findHospitalEntityById(UUID hospitalId);
}
