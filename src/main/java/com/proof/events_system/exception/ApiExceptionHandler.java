package com.proof.events_system.exception;

import com.proof.events_system.payload.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EventsException.class)
    public ResponseEntity<ErrorResponse> duplicatedResource(EventsException e, WebRequest request) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getDescription(), e.getReasons()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        List<String> reasons = new ArrayList<>();
        for(FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            reasons.add(String.format("%s - %s", fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return ResponseEntity.status(ApiError.VALIDATION_ERROR.getStatus())
                .body(new ErrorResponse(ApiError.VALIDATION_ERROR.getMessage(), reasons));
    }

}
