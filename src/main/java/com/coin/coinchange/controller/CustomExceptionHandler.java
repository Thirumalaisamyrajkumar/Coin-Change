package com.coin.coinchange.controller;

import com.coin.coinchange.response.ErrorResponse;
import com.coin.coinchange.exception.NotEnoughCoinException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotEnoughCoinException.class)
    protected ResponseEntity<ErrorResponse> mapResponse(NotEnoughCoinException e){
        return ResponseEntity.status(e.getHttpStatusCode()).body(
                ErrorResponse.builder().httpStatus(e.getHttpStatusCode())
                .error(e.getMessage()).build()
        );
    }
}
