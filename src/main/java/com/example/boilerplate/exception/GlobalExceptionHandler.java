package com.example.boilerplate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<RestExceptionResponse> resourceNotFound(AppException ex) {
        RestExceptionResponse response = new RestExceptionResponse();
        response.setErrorCode("INTERNAL_SERVER_ERROR");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<RestExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<RestExceptionResponse> resourceAlreadyExists(BadRequestException ex) {
        RestExceptionResponse response = new RestExceptionResponse();
        response.setErrorCode("BAD_REQUEST");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<RestExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestExceptionResponse> customException(ResourceNotFoundException ex) {
        RestExceptionResponse response = new RestExceptionResponse();
        response.setErrorCode("NOT_FOUND");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<RestExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<RestExceptionResponse> unauthorizedException(UnauthorizedAccessException ex) {
        RestExceptionResponse response = new RestExceptionResponse();
        response.setErrorCode("UNAUTHORIZED");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<RestExceptionResponse>(response, HttpStatus.UNAUTHORIZED);
    }
}
