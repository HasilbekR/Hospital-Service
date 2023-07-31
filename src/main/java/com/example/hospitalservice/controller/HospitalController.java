package com.example.hospitalservice.controller;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.example.hospitalservice.dto.HospitalRequestDto;
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


    @PostMapping("/addHospital")
    public HospitalEntity save(
            @RequestBody HospitalRequestDto hospitalRequestDto
            ){
        return hospitalService.addHospital(hospitalRequestDto);
    }

    @GetMapping("/getAllHospital")
    public List<HospitalEntity> getAll(
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size
    ){
        return hospitalService.getAll(page,size);
    }

    @PutMapping("/{hospitalId}/update")
    private HospitalEntity update(
            @PathVariable UUID hospitalId,
            @RequestBody HospitalRequestDto hospitalRequestDto
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
}
