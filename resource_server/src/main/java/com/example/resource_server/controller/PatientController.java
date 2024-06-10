package com.example.resource_server.controller;

import com.example.resource_server.dto.PatientDto;
import com.example.resource_server.dto.PatientRequestDto;
import com.example.resource_server.exception.ServiceRuntimeException;
import com.example.resource_server.service.PatientService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@Validated
public class PatientController {

      @Autowired
      private final PatientService service;

      public PatientController(PatientService service) {
            this.service = service;
      }

      @GetMapping
      @PreAuthorize("hasAnyRole('Practitioner', 'Patient') and hasAnyAuthority('SCOPE_Patient.Read')")
      @ApiResponses({
            @ApiResponse(
                  responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PatientDto.class)))
            ),
            @ApiResponse(
                  responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceRuntimeException.class))
            ),
            @ApiResponse(
                  responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceRuntimeException.class))
            )
      })
      public ResponseEntity<List<PatientDto>> getAllPatients() {
            List<PatientDto> allPatients = service.findAllPatients();

            return ResponseEntity.ok(allPatients);
      }

      @GetMapping("/{name}")
      @PreAuthorize("hasAnyRole('Practitioner', 'Patient') and hasAnyAuthority('SCOPE_Patient.Read')")
      @ApiResponses({
            @ApiResponse(
                  responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))
            ),
            @ApiResponse(
                  responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceRuntimeException.class))
            ),
            @ApiResponse(
                  responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceRuntimeException.class))
            )
      })
      public ResponseEntity<PatientDto> getPatientByName(@Size(min = 3) @PathVariable String name) {
            PatientDto patient = service.findPatientByName(name);

            return ResponseEntity.ok(patient);
      }

      @PostMapping
      @PreAuthorize("hasAnyRole('Practitioner') and hasAnyAuthority('SCOPE_Patient.Write')")
      @ApiResponses({
            @ApiResponse(
                  responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))
            ),
            @ApiResponse(
                  responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceRuntimeException.class))
            ),
            @ApiResponse(
                  responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceRuntimeException.class))
            )
      })
      public ResponseEntity<PatientDto> createPatient(
            @Valid @RequestBody PatientRequestDto patient
      ) {
            PatientDto patientFromDb = service.createPatient(patient);
            return ResponseEntity.ok(patientFromDb);
      }

      @PutMapping
      @PreAuthorize("hasAnyRole('Practitioner') and hasAnyAuthority('SCOPE_Patient.Write')")
      @ApiResponses({
            @ApiResponse(
                  responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))
            ),
            @ApiResponse(
                  responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceRuntimeException.class))
            ),
            @ApiResponse(
                  responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceRuntimeException.class))
            )
      })
      public ResponseEntity<PatientDto> updatePatient(
            @Valid @RequestBody PatientRequestDto patient
      ) {
            PatientDto patientFromDb = service.updatePatient(patient);
            return ResponseEntity.ok(patientFromDb);
      }

      @DeleteMapping("/{name}")
      @PreAuthorize("hasAnyRole('Practitioner') and hasAnyAuthority('SCOPE_Patient.Delete')")
      @ApiResponses({
            @ApiResponse(
                  responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                  responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceRuntimeException.class))
            ),
            @ApiResponse(
                  responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceRuntimeException.class))
            )
      })
      public ResponseEntity<String> deletePatientByName(
            @Size(min = 3) @PathVariable String name
      ) {
            service.deletePatientByName(name);
            return ResponseEntity.ok("Patient with name : " + name + " was deleted");
      }

}
