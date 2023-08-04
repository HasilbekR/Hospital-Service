package com.example.hospitalservice.service;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.example.hospitalservice.Entity.HospitalStatus;
import com.example.hospitalservice.dto.HospitalSaveDto;
import com.example.hospitalservice.exceptions.DataNotFoundException;
import com.example.hospitalservice.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HospitalService{
    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;

    public HospitalEntity addHospital(HospitalSaveDto newHospital){
        HospitalEntity hospitalEntity = modelMapper.map(newHospital, HospitalEntity.class);
        hospitalEntity.setStatus(HospitalStatus.IN_PREVENTION);
        return hospitalRepository.save(hospitalEntity);
    }


    public List<HospitalEntity> getAll(int page , int size){
        Pageable pageable = PageRequest.of(page, size);
        return hospitalRepository.findAll(pageable).getContent();
    }

    public void delete(UUID hospitalId){
        HospitalEntity hospitalEntity = hospitalRepository.findHospitalEntityById(hospitalId)
                .orElseThrow(() -> new DataNotFoundException("Hospital not found"));
        hospitalEntity.setStatus(HospitalStatus.NOT_ACTIVE);
        hospitalRepository.save(hospitalEntity);
    }


    public HospitalEntity update(UUID hospitalId, HospitalSaveDto update){
        HospitalEntity hospitalEntity = hospitalRepository
                .findHospitalEntityById(hospitalId).orElseThrow(
                        () -> new DataNotFoundException("Hospital not found!"));
        modelMapper.map(update ,hospitalEntity);
        return hospitalRepository.save(hospitalEntity);
    }

    public String getHospitalLocation(UUID hospitalId){
        HospitalEntity hospitalEntity = hospitalRepository.
                findHospitalEntityById(hospitalId).orElseThrow(
                        () -> new DataNotFoundException("Hospital Not found!"));
        return  "Address" +
                " = " + hospitalEntity.getAddress() + " : " + "Location = " +
                "https://www.google.com/maps/search/?api=1&query=" +
                hospitalEntity.getLocation()
                .getLatitude() + "," +
                hospitalEntity.getLocation().getLongitude();
    }
}
