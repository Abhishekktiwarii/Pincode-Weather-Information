package com.weather.pincode.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ProblemDetail handleApiError(HttpClientErrorException ex) {
        log.error("External API error: {}", ex.getMessage());

        var problem = ProblemDetail.forStatus(ex.getStatusCode());
        problem.setTitle("External API Error");
        problem.setDetail("Failed to fetch data from OpenWeather API");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("errorType", "API_ERROR");

        return problem;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleValidationError(ConstraintViolationException ex) {
        log.warn("Validation error: {}", ex.getMessage());

        var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Validation Failed");
        problem.setDetail("Invalid pincode format. Must be 6 digits.");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("errorType", "VALIDATION_ERROR");

        return problem;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleDateError(MethodArgumentTypeMismatchException ex) {
        log.warn("Invalid date format: {}", ex.getMessage());

        var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid Date Format");
        problem.setDetail("Date must be in yyyy-MM-dd format");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("errorType", "DATE_FORMAT_ERROR");

        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericError(Exception ex) {
        log.error("Unexpected error: ", ex);

        var problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Internal Server Error");
        problem.setDetail("An unexpected error occurred");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("errorType", "INTERNAL_ERROR");

        return problem;
    }
}