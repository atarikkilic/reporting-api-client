package com.finhouse.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleValidationErrors(WebExchangeBindException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", "BAD_REQUEST");
        errors.put("message", "Validation failed");
        errors.put("errors", ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage()
                ))
                .toList());

        return Mono.just(ResponseEntity.badRequest().body(errors));
    }

    @ExceptionHandler(ApiException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleApiException(ApiException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", ex.getStatus().name());
        error.put("message", ex.getMessage());

        return Mono.just(ResponseEntity.status(ex.getStatus()).body(error));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleGenericException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", "INTERNAL_SERVER_ERROR");
        error.put("message", "An unexpected error occurred");

        return Mono.just(ResponseEntity.internalServerError().body(error));
    }
}