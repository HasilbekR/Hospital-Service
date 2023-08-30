package com.example.hospitalservice.servcie;

import com.example.hospitalservice.Entity.HospitalEntity;
import com.example.hospitalservice.Entity.HospitalStatus;
import com.example.hospitalservice.Entity.LocationEntity;
import com.example.hospitalservice.dto.HospitalSaveDto;
import com.example.hospitalservice.exceptions.DataNotFoundException;
import com.example.hospitalservice.repository.HospitalRepository;
import com.example.hospitalservice.service.HospitalService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class HospitalServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private HospitalService hospitalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddHospital() {

        HospitalSaveDto hospitalSaveDto = new HospitalSaveDto();

        HospitalEntity hospitalEntity = new HospitalEntity();

        when(modelMapper.map(hospitalSaveDto, HospitalEntity.class)).thenReturn(hospitalEntity);

        when(hospitalRepository.save(any(HospitalEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        hospitalService.addHospital(hospitalSaveDto);

        verify(modelMapper).map(hospitalSaveDto, HospitalEntity.class);
        verify(hospitalRepository).save(hospitalEntity);

        assertEquals(HospitalStatus.IN_PREVENTION, hospitalEntity.getStatus());
    }

    @Test
    void testGetAll() {
        int page = 0;
        int size = 1;
        List<HospitalEntity> mockEntities = Arrays.asList(new HospitalEntity(), new HospitalEntity());  // Create as many mock entities as needed
        Page<HospitalEntity> mockPage = new PageImpl<>(mockEntities);

        when(hospitalRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        List<HospitalEntity> result = hospitalService.getAll(page, size);

        verify(hospitalRepository).findAll(PageRequest.of(page, size));
        assertEquals(mockEntities.size(), result.size());
        assertTrue(result.containsAll(mockEntities));
    }
    @Test
    void testDelete() {
        UUID testUUID = UUID.randomUUID();
        HospitalEntity mockEntity = new HospitalEntity();

        when(hospitalRepository.findHospitalEntityById(testUUID)).thenReturn(Optional.of(mockEntity));


        hospitalService.delete(testUUID);

        verify(hospitalRepository).findHospitalEntityById(testUUID);
        assertEquals(HospitalStatus.NOT_ACTIVE, mockEntity.getStatus());
        verify(hospitalRepository).save(mockEntity);
    }

    @Test
    void testDeleteHospitalNotFound() {
        UUID testUUID = UUID.randomUUID();

        when(hospitalRepository.findHospitalEntityById(testUUID)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            hospitalService.delete(testUUID);
        });
        verify(hospitalRepository).findHospitalEntityById(testUUID);
        verify(hospitalRepository, never()).save(any(HospitalEntity.class));
    }

    @Test
    void testUpdate() {
        UUID testUUID = UUID.randomUUID();
        HospitalSaveDto mockUpdateDto = new HospitalSaveDto();
        HospitalEntity mockEntity = new HospitalEntity();

        when(hospitalRepository.findHospitalEntityById(testUUID)).thenReturn(Optional.of(mockEntity));

        doAnswer(invocation -> {
            HospitalSaveDto source = invocation.getArgument(0);
            HospitalEntity target = invocation.getArgument(1);

            return null;
        }).when(modelMapper).map(mockUpdateDto, mockEntity);

        HospitalEntity result = hospitalService.update(testUUID, mockUpdateDto);

        verify(hospitalRepository).findHospitalEntityById(testUUID);
        verify(modelMapper).map(mockUpdateDto, mockEntity);
        verify(hospitalRepository).save(mockEntity);
    }

    @Test
    void testUpdateHospitalNotFound() {
        UUID testUUID = UUID.randomUUID();
        HospitalSaveDto mockUpdateDto = new HospitalSaveDto();

        when(hospitalRepository.findHospitalEntityById(testUUID)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            hospitalService.update(testUUID, mockUpdateDto);
        });
        verify(hospitalRepository).findHospitalEntityById(testUUID);
        verify(hospitalRepository, never()).save(any(HospitalEntity.class));
    }

    @Test
    void testGetHospitalLocation() {
        UUID testUUID = UUID.randomUUID();
        HospitalEntity hospitalEntity = new HospitalEntity();
        hospitalEntity.setAddress("testAddress");

        LocationEntity testLocation = new LocationEntity();
        testLocation.setLatitude("12.3456789");
        testLocation.setLongitude("12.3456789");

        hospitalEntity.setLocation(testLocation);

        when(hospitalRepository.findHospitalEntityById(testUUID)).thenReturn(Optional.of(hospitalEntity));

        String result = hospitalService.getHospitalLocation(testUUID);

        String expected = "1 = Address = testAddress \n 2 = Location = https://www.google.com/maps/@?api=1&map_action=map&center=12.3456789,12.3456789&zoom=15";
        assertEquals(expected, result);
    }

    @Test
    void testGetHospitalLocationNotFound() {
        UUID testUUID = UUID.randomUUID();

        when(hospitalRepository.findHospitalEntityById(testUUID)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            hospitalService.getHospitalLocation(testUUID);
        });
    }



}
