package com.example.proyectoapiv1.exceptions;

import org.apache.log4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> procesarErrorBadRequest(BadRequestException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> procesarErrorNotFound(ResourceNotFoundException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({ResourceConflictException.class})
    public ResponseEntity<String> procesarErrorConflict(ResourceConflictException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}

