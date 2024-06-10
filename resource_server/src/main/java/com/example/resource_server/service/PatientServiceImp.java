package com.example.resource_server.service;

import com.example.resource_server.dto.PatientDto;
import com.example.resource_server.dto.PatientRequestDto;
import com.example.resource_server.entity.Patient;
import com.example.resource_server.exception.ServiceRuntimeException;
import com.example.resource_server.repository.PatientRepository;
import jakarta.validation.constraints.Size;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class PatientServiceImp implements PatientService {

      private final PatientRepository repository;

      private final ModelMapper mapper;

      @Autowired
      public PatientServiceImp(PatientRepository repository, ModelMapper mapper) {
            this.repository = repository;
            this.mapper = mapper;
      }

      @Override
      public PatientDto createPatient(PatientRequestDto patient) {
            Patient patientFromDb = repository.save(
                  mapper.map(patient, Patient.class)
            );

            return mapper.map(patientFromDb, PatientDto.class);
      }

      @Override
      @Size(min = 1)
      public List<PatientDto> findAllPatients() {
            return repository.findAll().stream()
                  .map(patient -> mapper.map(patient, PatientDto.class))
                  .toList();
      }

      @Override
      public PatientDto findPatientByName(String name) {
            Patient patientFromDb = unwrapOptionalOrThrow(
                  name, repository.findPatientByName(name)
            );
            System.out.println();
            return mapper.map(patientFromDb, PatientDto.class);
      }

      @Override
      @Transactional
      public PatientDto updatePatient(PatientRequestDto patientDto) {
            Patient patientFromDb = unwrapOptionalOrThrow(
                  patientDto.getName(), repository.findPatientByName(patientDto.getName())
            );
            patientFromDb.setBirthday(patientDto.getBirthday());
            patientFromDb.setGender(patientDto.getGender());
            Patient patientAfterUpdate = repository.save(patientFromDb);
            return mapper.map(patientAfterUpdate, PatientDto.class);
      }

      @Override
      @Transactional
      public void deletePatientByName(String name) {
            Integer countOfDeleted = repository.deleteByName(name);
            if (countOfDeleted == null || countOfDeleted == 0) {
                  throw new ServiceRuntimeException(404, "Patient with name " + name + " isn't found");
            }
      }

      private Patient unwrapOptionalOrThrow(String name, Optional<Patient> optionalPatient) {
            return optionalPatient.orElseThrow(
                  () -> new ServiceRuntimeException(
                        404, "Patient with name " + name + " isn't found"
                  ));
      }
}
