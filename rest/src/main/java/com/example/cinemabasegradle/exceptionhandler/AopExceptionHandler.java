package com.example.cinemabasegradle.exceptionhandler;

import com.example.cinemabasegradle.exception.ExceptionDto;
import com.example.cinemabasegradle.exception.ResourceNotFoundException;
import com.example.cinemabasegradle.exception.SearchMovieException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class AopExceptionHandler {

    private static final Integer UNAUTHORIZED_CODE = 401;

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handleException(ResourceNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(ex.getMessage());
        exceptionDto.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionDto.setTimestamp(LocalDateTime.now());

        log.error("Caught ResourceNotFoundException - {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDto);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(ex.getMessage());
        exceptionDto.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDto.setTimestamp(LocalDateTime.now());

        log.error("Caught another exception exception: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionDto>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<ExceptionDto> exceptionDtoList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            ExceptionDto exceptionDto = new ExceptionDto();
            exceptionDto.setErrorMessage(String.format("%s - %s", fieldName, errorMessage));
            exceptionDto.setStatus(UNAUTHORIZED_CODE);
            exceptionDto.setTimestamp(LocalDateTime.now());
            exceptionDtoList.add(exceptionDto);
        });
        return ResponseEntity.badRequest().body(exceptionDtoList);
    }

    @ExceptionHandler(SearchMovieException.class)
    public ResponseEntity<ExceptionDto> handleSearchMovieException(
            RuntimeException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(ex.getMessage());
        exceptionDto.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDto.setTimestamp(LocalDateTime.now());

        log.error("Caught SearchMovieException exception: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDto);
    }
}

