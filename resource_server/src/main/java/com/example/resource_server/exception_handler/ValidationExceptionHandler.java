package com.example.resource_server.exception_handler;

import com.example.resource_server.dto.ValidationErrorDto;
import com.example.resource_server.violation.Violation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {
      @ExceptionHandler(ConstraintViolationException.class)
      @ResponseStatus(HttpStatus.BAD_REQUEST)
      ResponseEntity<ValidationErrorDto> handleConstraintValidationException(
            ConstraintViolationException e) {

            List<Violation> violations = e.getConstraintViolations().stream()
                  .map(constraintViolation -> new Violation(
                        constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage())
                  )
                  .toList();
            return new ResponseEntity<>(
                  new ValidationErrorDto(400, "Constraint validation exception", violations),
                  HttpStatus.BAD_REQUEST
            );
      }

      @ExceptionHandler(MethodArgumentNotValidException.class)
      @ResponseStatus(HttpStatus.BAD_REQUEST)
      ResponseEntity<ValidationErrorDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

            List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                  .map(fieldError -> new Violation(
                        fieldError.getField(), fieldError.getDefaultMessage())
                  )
                  .toList();

            return new ResponseEntity<>(
                  new ValidationErrorDto(400, "Method validation exception", violations),
                  HttpStatus.BAD_REQUEST
            );
      }

}