package com.example.hospitalservice.controller;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.example.hospitalservice.dto.HospitalSaveDto;
import com.example.hospitalservice.service.HospitalService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/getAll")
    public List<HospitalEntity> getAll(
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size
    ){
        return hospitalService.getAll(page,size);
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
}
