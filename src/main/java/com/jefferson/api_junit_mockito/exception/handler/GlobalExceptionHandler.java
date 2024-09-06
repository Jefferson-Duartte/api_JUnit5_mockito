package com.jefferson.api_junit_mockito.exception.handler;

import com.jefferson.api_junit_mockito.exception.ExceptionResponse;
import com.jefferson.api_junit_mockito.exception.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ExceptionResponse> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getRequestURI());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

}
