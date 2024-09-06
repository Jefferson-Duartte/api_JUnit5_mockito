package com.jefferson.api_junit_mockito.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
}
