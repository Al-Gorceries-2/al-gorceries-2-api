package com.algorceries.api.controller;

import java.util.List;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.algorceries.api.dto.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_KEY_VALIDATION = "VALIDATION";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .toList();

        return ResponseEntity.badRequest().body(new ErrorDto(ERROR_KEY_VALIDATION, messages));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> messages =
            ex.getConstraintViolations().stream().map(v -> v.getPropertyPath() + ": " + v.getMessage()).toList();

        return ResponseEntity.badRequest().body(new ErrorDto(ERROR_KEY_VALIDATION, messages));
    }
}
