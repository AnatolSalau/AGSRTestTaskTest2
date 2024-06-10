package com.example.resource_server.repository;

import com.example.resource_server.entity.Patient;
import com.example.resource_server.entity.enums.GenderType;
import com.example.resource_server.util.CustomDataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryTest {
      @Autowired
      private PatientRepository repository;

      private final String ADMIN_NAME = "admin";

      private Patient patient1 = new Patient("patient1", GenderType.MALE,
            CustomDataUtil.parseDataFromString("02-03-1990", CustomDataUtil.DATE_PATTERN)
      );

      @Test
      void givenPatient_whenSave_thenReturnPatient() {
            Patient save = repository.save(patient1);
            Assertions.assertEquals(patient1, save);
      }

      @Test
      void givenPatient_whenUpdateBirthday_thenReturnUpdatedPatient() {
            Optional<Patient> byId = repository.findPatientByName(ADMIN_NAME);
            byId.ifPresentOrElse((patient) -> {
                  patient.setBirthday(CustomDataUtil.parseDataFromString("10-10-2010", CustomDataUtil.DATE_PATTERN));
                  repository.save(patient);
            }, () -> {

            });
      }

}