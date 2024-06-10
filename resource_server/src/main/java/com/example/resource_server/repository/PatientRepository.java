package com.example.resource_server.repository;

import com.example.resource_server.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
      Optional<Patient> findPatientByName(String name);

      Integer deleteByName(String name);

}
