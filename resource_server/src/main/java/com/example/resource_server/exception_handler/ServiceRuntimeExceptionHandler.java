package com.example.resource_server.exception_handler;

import com.example.resource_server.dto.ServiceErrorDto;
import com.example.resource_server.exception.ServiceRuntimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceRuntimeExceptionHandler {

      @ExceptionHandler(value = {ServiceRuntimeException.class})
      public ResponseEntity<ServiceErrorDto> handleUserRuntimeException(ServiceRuntimeException serviceRuntimeException) {
            return ResponseEntity
                  .status(serviceRuntimeException.getStatusCode())
                  .body(new ServiceErrorDto(
                              serviceRuntimeException.getStatusCode(),
                              serviceRuntimeException.getMessage()
                        )
                  );
      }
}
