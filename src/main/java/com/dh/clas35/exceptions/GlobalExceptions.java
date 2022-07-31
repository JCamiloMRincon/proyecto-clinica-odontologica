package com.dh.clas35.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> tratamientoErrorResourceNotFound(ResourceNotFoundException rnfEx) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ATENCION: ERROR " +
                rnfEx.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> tratamientoErrorBadRequest(BadRequestException brEx) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ATENCION: ERROR " +
                brEx.getMessage());
    }

}
