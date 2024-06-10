package com.example.resource_server.dto;

import com.example.resource_server.violation.Violation;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class ValidationErrorDto extends ServiceErrorDto {
      public ValidationErrorDto(int statusCode, String message, List<Violation> violations) {
            super(statusCode, message);
            this.violations = violations;
      }

      public final List<Violation> violations;

}