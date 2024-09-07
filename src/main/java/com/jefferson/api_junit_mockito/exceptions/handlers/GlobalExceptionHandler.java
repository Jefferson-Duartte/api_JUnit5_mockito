package com.jefferson.api_junit_mockito.exceptions.handlers;

import com.jefferson.api_junit_mockito.exceptions.ExceptionResponse;
import com.jefferson.api_junit_mockito.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.jefferson.api_junit_mockito.exceptions.DataIntegrityViolationException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ExceptionResponse> buildResponseEntity(Exception exception,
                                                                  HttpStatus status,
                                                                  HttpServletRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(exceptionResponse);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ExceptionResponse> objectNotFound(
            ObjectNotFoundException exception,
            HttpServletRequest request) {
        return buildResponseEntity(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> dataIntegrityViolationException(
            DataIntegrityViolationException exception,
            HttpServletRequest request) {
        return buildResponseEntity(exception, HttpStatus.BAD_REQUEST, request);
    }
}
