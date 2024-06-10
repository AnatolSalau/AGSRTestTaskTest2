package com.example.resource_server.service;

import com.example.resource_server.dto.PatientDto;
import com.example.resource_server.dto.PatientRequestDto;
import com.example.resource_server.entity.Patient;
import com.example.resource_server.entity.enums.GenderType;
import com.example.resource_server.repository.PatientRepository;
import com.example.resource_server.util.CustomDataUtil;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.UUID;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class PatientServiceImpTest {

      @MockBean
      PatientRepository patientRepository;

      @MockBean
      ModelMapper mapper;

      @Autowired
      private PatientServiceImp patientService;

      UUID patientId = UUID.randomUUID();

      PatientRequestDto patientRequestDto = new PatientRequestDto(
            "patient1", GenderType.MALE,
            CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN)
      );

      PatientDto patientDto = new PatientDto(
            patientId, "patient1", GenderType.MALE,
            CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN)
      );

      Patient patientFromDb = new Patient(patientId, "patient1", GenderType.MALE,
            CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN),
            new Date(),
            new Date()
      );

      @Test
      void givenPatientRequestDto_whenSave_thenReturnPatientDto() {
            Mockito.when(patientRepository.save(mapper.map(patientRequestDto, Patient.class)))
                  .thenReturn(patientFromDb);
            Mockito.when(mapper.map(patientFromDb, PatientDto.class))
                  .thenReturn(patientDto);

            PatientDto result = patientService.createPatient(patientRequestDto);
            Assertions.assertEquals(patientDto, result);
      }

      @Test
      void givenPatientRequestDto_whenSaveAndReturnNull_thenThrow() {
            Mockito.when(patientRepository.save(mapper.map(patientRequestDto, Patient.class)))
                  .thenReturn(patientFromDb);
            Mockito.when(mapper.map(patientFromDb, PatientDto.class))
                  .thenReturn(null);

            Assertions.assertThrows(ConstraintViolationException.class, () -> {
                  patientService.createPatient(patientRequestDto);
            });
      }
}