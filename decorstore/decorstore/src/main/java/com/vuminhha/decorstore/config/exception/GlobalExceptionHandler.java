package com.vuminhha.decorstore.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String>handlingRuntimeException(RuntimeException exception)
    {
        return ResponseEntity.badRequest().body((exception.getMessage()));
    }
    @ExceptionHandler( value = ResourceNotFoundException.class)
    public ResponseEntity<String>handleNotFound(ResourceNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
