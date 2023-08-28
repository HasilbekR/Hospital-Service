package com.example.hospitalservice.service;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.example.hospitalservice.Entity.HospitalStatus;
import com.example.hospitalservice.dto.ExchangeDataDto;
import com.example.hospitalservice.dto.HospitalData;
import com.example.hospitalservice.dto.HospitalSaveDto;
import com.example.hospitalservice.exceptions.DataNotFoundException;
import com.example.hospitalservice.exceptions.UserBadRequestException;
import com.example.hospitalservice.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    public HospitalData getAll(){
        return HospitalData.builder()
                .hospitals(hospitalRepository.findAllHospitals())
                .cities(hospitalRepository.getHospitalCities()).build();
    }

    public List<HospitalEntity> getAllByCity(String city){
        return hospitalRepository.findHospitalEntityByCity(city);
    }

    public UUID getHospital(ExchangeDataDto dataDto){
        return hospitalRepository.findHospitalEntityById(UUID.fromString(dataDto.getSource())).orElseThrow(()-> new DataNotFoundException("Hospital not found")).getId();
    }
    public HospitalEntity getHospitalById(UUID hospitalId){
        return hospitalRepository.findHospitalEntityById(hospitalId).orElseThrow(()-> new DataNotFoundException("Hospital not found"));
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

    public String getHospitalLocation(UUID hospitalId) {
        HospitalEntity hospitalEntity = hospitalRepository
                .findHospitalEntityById(hospitalId)
                .orElseThrow(() -> new DataNotFoundException("Hospital Not found!"));

        return "1 = Address = " + hospitalEntity.getAddress() + " \n 2 = Location = " +
                "https://www.google.com/maps/@?api=1&map_action=map&center=" +
                hospitalEntity.getLocation().getLatitude() + "," +
                hospitalEntity.getLocation().getLongitude() + "&zoom=15";
    }

    public HospitalEntity changeStatus(UUID hospitalId, String status) {
        HospitalEntity hospitalEntity = hospitalRepository
                .findHospitalEntityById(hospitalId).orElseThrow(
                        () -> new DataNotFoundException("Hospital not found!"));

        switch (status.toUpperCase()) {
            case "ACTIVE" -> hospitalEntity.setStatus(HospitalStatus.ACTIVE);
            case "IN_PREVENTION" -> hospitalEntity.setStatus(HospitalStatus.IN_PREVENTION);
            case "NOT_ACTIVE" -> hospitalEntity.setStatus(HospitalStatus.NOT_ACTIVE);
            default -> throw new UserBadRequestException("Invalid status: " + status);
        }
        hospitalRepository.save(hospitalEntity);
        return hospitalEntity;
    }

}
