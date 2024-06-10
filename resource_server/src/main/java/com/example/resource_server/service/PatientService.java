package com.example.resource_server.service;

import com.example.resource_server.dto.PatientDto;
import com.example.resource_server.dto.PatientRequestDto;
import com.example.resource_server.entity.Patient;

import java.util.List;
import java.util.UUID;

public interface PatientService {
      PatientDto createPatient(PatientRequestDto patient);
      List<PatientDto> findAllPatients();
      PatientDto findPatientByName(String name);
      PatientDto updatePatient(PatientRequestDto patient);
      void deletePatientByName(String name);

}
