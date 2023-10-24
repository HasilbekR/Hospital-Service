package com.example.hospitalservice.controller;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.example.hospitalservice.dto.*;
import com.example.hospitalservice.dto.response.StandardResponse;
import com.example.hospitalservice.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;
    @PostMapping("/save")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public StandardResponse<HospitalEntity> save(
            @RequestBody HospitalSaveDto hospitalRequestDto,
            Principal principal
            ){
        return hospitalService.addHospital(hospitalRequestDto, principal);
    }

    @GetMapping("/get-all")
    public StandardResponse<HospitalData> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String name

    ){
        if(page != 0) page = page-1;
        return hospitalService.getAll(page, size, city, name);
    }
    @GetMapping("/{hospitalId}/get-hospital")
    public StandardResponse<HospitalEntity> getHospital(
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
    @PostMapping("/send-name")
    public String getName(
            @RequestBody ExchangeDataDto dataDto
    ){
        return hospitalService.getName(dataDto);
    }
    @PutMapping("/{hospitalId}/update")
    @PreAuthorize(value = "hasRole('ADMIN')")
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

    @PutMapping("/{hospitalId}/changeStatus")
    @PreAuthorize(value = "hasRole('ADMIN') and hasAuthority('CHANGE_STATUS')")
    public StandardResponse<HospitalEntity> changeStatus(
            @PathVariable UUID hospitalId,
            @RequestParam String status
    ){
        return hospitalService.changeStatus(hospitalId, status);
    }
}
