package com.example.hospitalservice.repository;

import com.example.hospitalservice.Entity.HospitalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HospitalRepository extends JpaRepository<HospitalEntity, UUID>{

    Optional<HospitalEntity> findHospitalEntityById(UUID hospitalId);
    HospitalEntity findHospitalEntitiesById(UUID hospitalId);
    Page<HospitalEntity> findHospitalEntityByCity(String city, Pageable pageable);
    @Query(value = "select h from hospital h where h.status = 'OPEN'")
    Page<HospitalEntity> findAll(Pageable pageable);
    @Query(value = "select distinct h.city from hospital h")
    List<String> getHospitalCities();
}
