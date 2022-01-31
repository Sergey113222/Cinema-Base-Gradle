package com.example.cinemabasegradle.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    @ExceptionHandler(value = JwtAuthenticationException.class)
    public ResponseEntity<ExceptionDto> handleException(JwtAuthenticationException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(ex.getMessage());
        exceptionDto.setStatus(HttpStatus.UNAUTHORIZED.value());
        exceptionDto.setTimestamp(LocalDateTime.now());

        log.error("Caught JwtException - {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionDto);
    }
}
