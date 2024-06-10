package com.example.resource_server.controller;

import com.example.resource_server.dto.PatientDto;
import com.example.resource_server.dto.PatientRequestDto;
import com.example.resource_server.entity.enums.GenderType;
import com.example.resource_server.service.PatientService;
import com.example.resource_server.util.CustomDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientControllerTest {
      @Autowired
      private MockMvc mvc;

      @Autowired
      private ObjectMapper mapper;

      @MockBean
      PatientService service;

      @Autowired
      private WebApplicationContext webApplicationContext;


      @Test
      @WithMockUser(username = "user", password = "user", authorities = {"ROLE_Practitioner", "Patient.Write"})
      void givenPatientDto_whenAuthoritiesAreRight_thenCreateAndReturnPatient() throws Exception {
            PatientRequestDto patientRequestDtoGenderNull = new PatientRequestDto(
                  "patient1", GenderType.MALE,
                  CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN)
            );

            PatientDto patientDto = new PatientDto(UUID.randomUUID(), "patient1", GenderType.MALE,
                  CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN)
            );

            String patientRequestJson = mapper.writeValueAsString(patientRequestDtoGenderNull);
            Mockito
                  .when(service.createPatient(patientRequestDtoGenderNull))
                  .thenReturn(patientDto);
            mvc
                  .perform(MockMvcRequestBuilders
                        .post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientRequestJson)
                  )
                  .andExpect(MockMvcResultMatchers.status().isOk());
      }

      @Test
      @WithMockUser(username = "user", password = "user", authorities = {"ROLE_Practitioner", "Patient.Write"})
      void givenPatientDto_whenGenderIsNull_thenThrow() throws Exception {
            PatientRequestDto patientRequestDtoGenderNull = new PatientRequestDto(
                  "patient1", /*GenderType.MALE*/null,
                  CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN)
            );

            PatientDto patientDto = new PatientDto(UUID.randomUUID(), "patient1", GenderType.MALE,
                  CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN)
            );

            String patientRequestJson = mapper.writeValueAsString(patientRequestDtoGenderNull);
            Mockito
                  .when(service.createPatient(patientRequestDtoGenderNull))
                  .thenReturn(patientDto);
            mvc
                  .perform(MockMvcRequestBuilders
                        .post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientRequestJson)
                  )
                  .andExpect(MockMvcResultMatchers.status().isBadRequest());
      }

      @Test
      @WithMockUser(username = "user", password = "user", authorities = {"ROLE_USER", "Patient.Write"})
      void givenPreAuthorizeRoleAdmin_whenRoleUser_thenThrow() throws Exception {
            PatientRequestDto patientRequestDtoGenderNull = new PatientRequestDto(
                  "patient1", GenderType.MALE,
                  CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN)
            );

            PatientDto patientDto = new PatientDto(UUID.randomUUID(), "patient1", GenderType.MALE,
                  CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN)
            );

            String patientRequestJson = mapper.writeValueAsString(patientRequestDtoGenderNull);
            Mockito
                  .when(service.createPatient(patientRequestDtoGenderNull))
                  .thenReturn(patientDto);
            Assertions
                  .assertThrows(ServletException.class, () ->
                        mvc
                              .perform(MockMvcRequestBuilders
                                    .post("/api/v1/patients")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(patientRequestJson)
                              )
                  );
      }

      @Test
      @WithMockUser(username = "user", password = "user", authorities = {"ROLE_Patient", "SCOPE_Patient.Read"})
      void givenPreAuthorizeRolePatient_whenRolePatient_thenReturnAllPatients() throws Exception {
            List<PatientDto> patientList = List.of(new PatientDto(UUID.randomUUID(), "patient1", GenderType.MALE,
                  CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN)
            ));
            Mockito
                  .when(service.findAllPatients())
                  .thenReturn(patientList);
            mvc
                  .perform(MockMvcRequestBuilders
                        .get("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                  ).andExpect(MockMvcResultMatchers.status().isOk());
      }

      @Test
      @WithMockUser(username = "user", password = "user", authorities = {"ROLE_Practitioner", "Patient.Read"})
      void givenPatientName_whenRolePractitioner_thenReturnPatient() throws Exception {
            PatientDto patientDto = new PatientDto(UUID.randomUUID(), "patient1", GenderType.MALE,
                  CustomDataUtil.parseDataFromString("20-03-1990", CustomDataUtil.DATE_PATTERN)
            );
            Mockito
                  .when(service.findPatientByName("patient1"))
                  .thenReturn(patientDto);
            mvc
                  .perform(MockMvcRequestBuilders
                        .get("/api/v1/patients/patient1")
                  ).andExpect(MockMvcResultMatchers.status().isOk());
      }

      @Test
      @WithMockUser(username = "user", password = "user", authorities = {"ROLE_Practitioner", "Patient.Read"})
      void givenPatientName_whenNameLengthLessThan3_thenThrow() {
            Assertions
                  .assertThrows(ServletException.class, () ->
                        mvc
                              .perform(MockMvcRequestBuilders
                                    .get("/api/v1/patients/aa")
                              )
                  );
      }

      @BeforeAll
      public void setup() {
            mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
      }
}