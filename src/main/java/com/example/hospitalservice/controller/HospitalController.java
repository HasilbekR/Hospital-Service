package com.example.hospitalservice.controller;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.example.hospitalservice.dto.*;
import com.example.hospitalservice.dto.response.StandardResponse;
import com.example.hospitalservice.service.HospitalService;
import lombok.RequiredArgsConstructor;
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
    public StandardResponse<HospitalEntity> save(
            @RequestBody HospitalSaveDto hospitalRequestDto
            ){
        return hospitalService.addHospital(hospitalRequestDto);
    }

    @GetMapping("/get-all")
    public StandardResponse<HospitalData> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return hospitalService.getAll(page, size);
    }
    @GetMapping("/get-all-by-city")
    public StandardResponse<List<HospitalInfo>> getAllByCity(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam String city
    ){
        return hospitalService.getAllByCity(city, page, size);
    }
    @GetMapping("/{hospitalId}/get-hospital")
    public StandardResponse<HospitalForFront> getHospital(
            @PathVariable UUID hospitalId
    ){
        return hospitalService.getHospitalById(hospitalId);
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
    @PostMapping("/send-address")
    public String getAddress(
            @RequestBody ExchangeDataDto dataDto
    ){
        return hospitalService.getAddress(dataDto);
    }
    @PutMapping("/{hospitalId}/update")
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    private StandardResponse<HospitalEntity> update(
            @PathVariable UUID hospitalId,
            @RequestBody HospitalSaveDto hospitalRequestDto
            ){
        return hospitalService.update(hospitalId ,hospitalRequestDto);
    }

    @DeleteMapping("/{hospitalId}/delete")
    @PreAuthorize(value = "hasRole('OWNER')")
    public StandardResponse<String> delete(
            @PathVariable UUID hospitalId
    ){
        return hospitalService.delete(hospitalId);
    }

    @GetMapping("/{hospitalId}/getLocation")
    public String getLocation(
            @PathVariable UUID hospitalId
    ){
       return hospitalService.getHospitalLocation(hospitalId);
    }

    @PutMapping("/{hospitalId}/changeStatus")
    @PreAuthorize(value = "hasRole('ADMIN') and hasAuthority('CHANGE_STATUS')")
    public StandardResponse<HospitalEntity> changeStatus(
            @PathVariable UUID hospitalId,
            @RequestParam String status
    ){
        return hospitalService.changeStatus(hospitalId, status);
    }
}
