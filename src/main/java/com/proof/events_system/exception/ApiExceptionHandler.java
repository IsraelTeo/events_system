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

@RestControllerAdvice////Se utiliza para manejar excepciones globalmente, pero también devuelve respuestas como JSON
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    //Extiende o hereda de ResponseEntityExceptionHandler: Proporciona manejo de excepciones predefinido para los controladores REST.
    // Viene con una serie de métodos que pueden ser sobrecargados para manejar ciertas excepciones comunes que ocurren durante la ejecución
    // de las solicitudes.


    //Cuando una excepción de un tipo específico (en este caso RestaurantException) es lanzada durante la ejecución de una solicitud en la API,
    // este método anotado con @ExceptionHandler se ejecutará para manejar esa excepción.
    @ExceptionHandler(EventsException.class)
    public ResponseEntity<ErrorResponse> duplicatedResource(EventsException e, WebRequest request) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getDescription(), e.getReasons()));
    }

    //Construye una representación legible del error para cada campo con un error de validación y la agrega a la lista reasons.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        List<String> reasons = new ArrayList<>();
        // Iteramos todos los campos que tienen un error y los vamos a agregar dentro del array con un formato definido
        // por nosotros
        for(FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            reasons.add(String.format("%s - %s", fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return ResponseEntity.status(ApiError.VALIDATION_ERROR.getStatus())
                .body(new ErrorResponse(ApiError.VALIDATION_ERROR.getMessage(), reasons));

    }


}
