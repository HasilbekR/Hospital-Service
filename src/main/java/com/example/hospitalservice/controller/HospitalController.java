package com.example.hospitalservice.controller;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.example.hospitalservice.dto.ExchangeDataDto;
import com.example.hospitalservice.dto.HospitalData;
import com.example.hospitalservice.dto.HospitalInfo;
import com.example.hospitalservice.dto.HospitalSaveDto;
import com.example.hospitalservice.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;
    @PostMapping("/save")
    @PreAuthorize(value = "hasRole('OWNER')")
    public ResponseEntity<HospitalEntity> save(
            @RequestBody HospitalSaveDto hospitalRequestDto
            ){
        return ResponseEntity.ok(hospitalService.addHospital(hospitalRequestDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<HospitalData> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return ResponseEntity.ok(hospitalService.getAll(page, size));
    }
    @GetMapping("/get-all-by-city")
    public List<HospitalInfo> getAllByCity(
            @RequestParam String city
    ){
        return hospitalService.getAllByCity(city);
    }
    @GetMapping("/{hospitalId}/get-hospital")
    public ResponseEntity<HospitalEntity> getHospital(
            @PathVariable UUID hospitalId
    ){
        return ResponseEntity.ok(hospitalService.getHospitalById(hospitalId));
    }
    @PostMapping("/send-id")
    public UUID getHospital(
            @RequestBody ExchangeDataDto dataDto
    ){
        if(hospitalService.getHospital(dataDto) != null) {
            return hospitalService.getHospital(dataDto);
        }
        return null;
    }
    @PutMapping("/{hospitalId}/update")
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    private ResponseEntity<HospitalEntity> update(
            @PathVariable UUID hospitalId,
            @RequestBody HospitalSaveDto hospitalRequestDto
            ){
        return ResponseEntity.ok(hospitalService.update(hospitalId ,hospitalRequestDto));
    }

    @DeleteMapping("/{hospitalId}/delete")
    @PreAuthorize(value = "hasRole('OWNER')")
    public ResponseEntity<String> delete(
            @PathVariable UUID hospitalId
    ){
        hospitalService.delete(hospitalId);
        return ResponseEntity.ok("SuccessFully deleted!");
    }

    @GetMapping("/{hospitalId}/getLocation")
    public String getLocation(
            @PathVariable UUID hospitalId
    ){
       return hospitalService.getHospitalLocation(hospitalId);
    }

    @PutMapping("/{hospitalId}/changeStatus")
    @PreAuthorize(value = "hasRole('ADMIN') and hasAuthority('CHANGE_STATUS')")
    public ResponseEntity<HospitalEntity> changeStatus(
            @PathVariable UUID hospitalId,
            @RequestParam String status
    ){
        return ResponseEntity.ok(hospitalService.changeStatus(hospitalId, status));
    }
}
