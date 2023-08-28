package com.example.hospitalservice.controller;

import com.example.hospitalservice.Entity.HospitalEntity;
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
    public ResponseEntity<HospitalEntity> save(
            @RequestBody HospitalSaveDto hospitalRequestDto
            ){
        return ResponseEntity.ok(hospitalService.addHospital(hospitalRequestDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<HospitalEntity>> getAll(
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size
    ){
        return ResponseEntity.ok(hospitalService.getAll(page,size));
    }

    @PutMapping("/{hospitalId}/update")
    private ResponseEntity<HospitalEntity> update(
            @PathVariable UUID hospitalId,
            @RequestBody HospitalSaveDto hospitalRequestDto
            ){
        return ResponseEntity.ok(hospitalService.update(hospitalId ,hospitalRequestDto));
    }

    @DeleteMapping("/{hospitalId}/delete")
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
    public ResponseEntity<HospitalEntity> changeStatus(
            @PathVariable UUID hospitalId,
            @RequestParam String status
    ){
        return ResponseEntity.ok(hospitalService.changeStatus(hospitalId, status));
    }
}
