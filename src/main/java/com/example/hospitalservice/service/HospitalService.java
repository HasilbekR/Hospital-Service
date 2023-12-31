package com.example.hospitalservice.service;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.example.hospitalservice.Entity.HospitalStatus;
import com.example.hospitalservice.dto.*;
import com.example.hospitalservice.dto.response.StandardResponse;
import com.example.hospitalservice.dto.response.Status;
import com.example.hospitalservice.exceptions.DataNotFoundException;
import com.example.hospitalservice.exceptions.UserBadRequestException;
import com.example.hospitalservice.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HospitalService{
    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public StandardResponse<HospitalEntity> addHospital(HospitalSaveDto newHospital, Principal principal) {
        HospitalEntity hospitalEntity = modelMapper.map(newHospital, HospitalEntity.class);
        hospitalEntity.setStatus(HospitalStatus.OPEN);
        HospitalEntity save = hospitalRepository.save(hospitalEntity);
        userService.setEmployment(principal, hospitalEntity.getId());

        return StandardResponse.<HospitalEntity>builder()
                .status(Status.SUCCESS)
                .message("Successfully created")
                .data(save)
                .build();
    }

    public StandardResponse<HospitalData> getAll(int page, int size, String city, String name){
        if(city != null) return getAllByCity(city, page, size);
        if(name != null) return getHospitalByName(name);
        Pageable pageable = PageRequest.of(page, size);
        List<HospitalEntity> hospitalEntities = hospitalRepository.findAll(pageable).getContent();
        int hospitalNumbers = hospitalRepository.findAll().size();
        int pagesCount = hospitalNumbers/size;
        if(hospitalNumbers%size != 0) pagesCount++;
        List<HospitalInfo> hospitalInfoList = new ArrayList<>();
        for (HospitalEntity hospitalEntity : hospitalEntities) {
            hospitalInfoList.add(HospitalInfo.builder()
                    .id(hospitalEntity.getId())
                    .name(hospitalEntity.getName())
                    .city(hospitalEntity.getCity())
                    .build());
        }
        return StandardResponse.<HospitalData>builder()
                .status(Status.SUCCESS)
                .message("All hospitals")
                .data(HospitalData.builder()
                        .hospitals(hospitalInfoList)
                        .cities(hospitalRepository.getHospitalCities())
                        .pagesCount(pagesCount)
                        .build())
                .build();
    }

    public StandardResponse<HospitalData> getAllByCity(String city, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<HospitalEntity> hospitalEntities = hospitalRepository.findHospitalEntityByCity(city, pageable).getContent();
        int hospitalNumbers = hospitalRepository.findAllByCity(city).size();
        int pagesCount = hospitalNumbers/size;
        if(hospitalNumbers%size != 0) pagesCount++;
        List<HospitalInfo> hospitalInfoList = new ArrayList<>();
        for (HospitalEntity hospitalEntity : hospitalEntities) {
            hospitalInfoList.add(HospitalInfo.builder()
                    .id(hospitalEntity.getId())
                    .name(hospitalEntity.getName())
                    .city(hospitalEntity.getCity())
                    .build());
        }
        return StandardResponse.<HospitalData>builder().status(Status.SUCCESS)
                .message("All hospitals in "+city)
                .data(HospitalData.builder().hospitals(hospitalInfoList).pagesCount(pagesCount).build())
                .build();
    }

    public UUID getHospital(ExchangeDataDto dataDto){
        return hospitalRepository.findHospitalEntitiesById(UUID.fromString(dataDto.getSource())).getId();
    }
    public StandardResponse<HospitalEntity> getHospitalById(UUID hospitalId){
        return StandardResponse.<HospitalEntity>builder().status(Status.SUCCESS)
                .message("Hospital entity")
                .data(hospitalRepository.findHospitalEntityById(hospitalId).orElseThrow(() -> new DataNotFoundException("Hospital not found")))
                .build();
    }

    public StandardResponse<String> delete(UUID hospitalId){
        HospitalEntity hospitalEntity = hospitalRepository.findHospitalEntityById(hospitalId)
                .orElseThrow(() -> new DataNotFoundException("Hospital not found"));
        hospitalEntity.setStatus(HospitalStatus.CLOSED);
        hospitalRepository.save(hospitalEntity);
        return StandardResponse.<String>builder().status(Status.SUCCESS).message("Successfully deleted").build();
    }


    public StandardResponse<HospitalEntity> update(UUID hospitalId, HospitalSaveDto update){
        HospitalEntity hospitalEntity = hospitalRepository
                .findHospitalEntityById(hospitalId).orElseThrow(
                        () -> new DataNotFoundException("Hospital not found!"));
        modelMapper.map(update ,hospitalEntity);
        return StandardResponse.<HospitalEntity>builder().status(Status.SUCCESS)
                .message("Successfully updated")
                .data(hospitalRepository.save(hospitalEntity))
                .build();
    }

    public StandardResponse<HospitalEntity> changeStatus(UUID hospitalId, String status) {
        HospitalEntity hospitalEntity = hospitalRepository
                .findHospitalEntityById(hospitalId).orElseThrow(
                        () -> new DataNotFoundException("Hospital not found!"));

        switch (status.toUpperCase()) {
            case "ACTIVE" -> hospitalEntity.setStatus(HospitalStatus.OPEN);
            case "CLOSED" -> hospitalEntity.setStatus(HospitalStatus.CLOSED);
            default -> throw new UserBadRequestException("Invalid status: " + status);
        }
        hospitalRepository.save(hospitalEntity);
        return StandardResponse.<HospitalEntity>builder()
                .status(Status.SUCCESS)
                .message("Status successfully changed to "+status)
                .data(hospitalEntity)
                .build();
    }

    public String getAddress(ExchangeDataDto dataDto) {
        return hospitalRepository.findHospitalEntitiesById(UUID.fromString(dataDto.getSource())).getAddress();
    }

    public StandardResponse<HospitalData> getHospitalByName(String name) {
        List<HospitalEntity> hospitals = hospitalRepository.findHospitalEntitiesByNameContainingIgnoreCase(name);
        List<HospitalInfo> hospitalInfoList = new ArrayList<>();
        for (HospitalEntity hospitalEntity : hospitals) {
            hospitalInfoList.add(HospitalInfo.builder()
                    .id(hospitalEntity.getId())
                    .name(hospitalEntity.getName())
                    .city(hospitalEntity.getCity())
                    .build());
        }
        return StandardResponse.<HospitalData>builder()
                .status(Status.SUCCESS)
                .message("Hospital entity")
                .data(HospitalData.builder().hospitals(hospitalInfoList)
                        .build())
                .build();

    }

    public String getName(ExchangeDataDto dataDto) {
        HospitalEntity hospital = hospitalRepository.findById(UUID.fromString(dataDto.getSource())).orElseThrow(() -> new DataNotFoundException("Hospital not found"));
        return hospital.getName();
    }
}
