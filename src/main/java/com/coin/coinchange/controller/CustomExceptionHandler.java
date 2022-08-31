package com.coin.coinchange.controller;

import com.coin.coinchange.response.ErrorResponse;
import com.coin.coinchange.exception.NotEnoughCoinException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotEnoughCoinException.class)
    protected ResponseEntity<ErrorResponse> mapResponse(NotEnoughCoinException e){
        return ResponseEntity.status(e.getHttpStatusCode()).body(
                ErrorResponse.builder().httpStatus(e.getHttpStatusCode())
                .error(e.getMessage()).build()
        );
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
