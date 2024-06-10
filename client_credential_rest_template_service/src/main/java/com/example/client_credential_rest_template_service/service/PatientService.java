package com.example.client_credential_rest_template_service.service;

import com.example.client_credential_rest_template_service.component.ServiceShutdownComponent;
import com.example.client_credential_rest_template_service.dto.PatientDto;
import com.example.client_credential_rest_template_service.dto.PatientRequestDto;
import com.example.client_credential_rest_template_service.enums.GenderType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.stream.IntStream;

@Service
public class PatientService {

      @Value("${messages.base-uri}")
      String resourceServerUrl;

      private final RestTemplateBuilder restTemplateBuilderConfigured;

      private final ObjectMapper objectMapper;

      private final ServiceShutdownComponent serviceShutdownComponent;

      @Autowired
      public PatientService(RestTemplateBuilder restTemplateBuilderConfigured, ObjectMapper objectMapper,
                            ServiceShutdownComponent serviceShutdownComponent
      ) {
            this.restTemplateBuilderConfigured = restTemplateBuilderConfigured;
            this.objectMapper = objectMapper;
            this.serviceShutdownComponent = serviceShutdownComponent;
      }

      @EventListener(ApplicationReadyEvent.class)
      public void post100PatientsAfterStartupThenShutdown() {
            RestTemplate restTemplate = restTemplateBuilderConfigured.build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            IntStream.range(3, 103).forEach((index) -> {
                        String json = null;
                        try {
                              json = objectMapper.writeValueAsString(
                                    new PatientRequestDto(
                                          "Patient" + index, GenderType.FEMALE, new Date()
                                    )
                              );
                        } catch (JsonProcessingException e) {
                              throw new RuntimeException(e);
                        }
                        HttpEntity<String> request = new HttpEntity<String>(json, headers);
                        restTemplate.postForEntity(
                              resourceServerUrl, request, PatientDto.class
                        );
                  }
            );
            serviceShutdownComponent.initiateShutdown(0);
      }
}
