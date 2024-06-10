package com.example.client_credential_rest_template_service.dto;


import com.example.client_credential_rest_template_service.enums.GenderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDto {
      @NotBlank
      @Size(min = 3)
      private String name;

      @NotNull
      private GenderType gender;

      @NotNull
      private Date birthday;
}
