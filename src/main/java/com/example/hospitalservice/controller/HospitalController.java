package com.example.hospitalservice.controller;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.example.hospitalservice.dto.ExchangeDataDto;
import com.example.hospitalservice.dto.HospitalData;
import com.example.hospitalservice.dto.HospitalSaveDto;
import com.example.hospitalservice.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hospital/api/v1")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;


    @PostMapping("/save")
    public HospitalEntity save(
            @RequestBody HospitalSaveDto hospitalRequestDto
            ){
        return hospitalService.addHospital(hospitalRequestDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<HospitalData> getAll(){
        return ResponseEntity.ok(hospitalService.getAll());
    }
    @GetMapping("/get-all-by-city")
    public List<HospitalEntity> getAllByCity(
            @RequestParam String city
    ){
        return hospitalService.getAllByCity(city);
    }
    @PostMapping("/exchange-hospital-id")
    public String getHospital(
            @RequestBody ExchangeDataDto dataDto
    ){
        return hospitalService.getHospital(dataDto).toString();
    }
    @PutMapping("/{hospitalId}/update")
    private HospitalEntity update(
            @PathVariable UUID hospitalId,
            @RequestBody HospitalSaveDto hospitalRequestDto
            ){
        return hospitalService.update(hospitalId ,hospitalRequestDto);
    }

    @DeleteMapping("/{hospitalId}/delete")
    public String delete(
            @PathVariable UUID hospitalId
    ){
        hospitalService.delete(hospitalId);
        return "SuccessFully deleted!!";
    }

    @GetMapping("/{hospitalId}/getLocation")
    public String getLocation(
            @PathVariable UUID hospitalId
    ){
       return hospitalService.getHospitalLocation(hospitalId);
    }

    @PutMapping("/{hospitalId}/changeStatus")
    public HospitalEntity changeStatus(
            @PathVariable UUID hospitalId,
            @RequestParam String status
    ){
        return hospitalService.changeStatus(hospitalId, status);
    }
}
