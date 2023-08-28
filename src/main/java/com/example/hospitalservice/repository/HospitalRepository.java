package com.example.hospitalservice.repository;

import com.example.hospitalservice.Entity.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HospitalRepository extends JpaRepository<HospitalEntity, UUID>{

    Optional<HospitalEntity> findHospitalEntityById(UUID hospitalId);
    List<HospitalEntity> findHospitalEntityByCity(String city);
    @Query(value = "select h from hospital h where h.status = 'ACTIVE'")
    List<HospitalEntity> findAllHospitals();
    @Query(value = "select distinct h.city from hospital h")
    List<String> getHospitalCities();
}
