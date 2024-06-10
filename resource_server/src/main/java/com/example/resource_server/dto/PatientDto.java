package com.example.resource_server.dto;

import com.example.resource_server.entity.enums.GenderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
      @NotNull
      private UUID id;

      @NotBlank
      @Size(min = 3)
      private String name;

      @NotNull
      private GenderType gender;

      @NotNull
      private Date birthday;
}
