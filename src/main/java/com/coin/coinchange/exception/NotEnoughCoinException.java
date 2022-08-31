package com.coin.coinchange.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotEnoughCoinException extends RuntimeException{
    private int httpStatusCode;

    public NotEnoughCoinException(String message, int httpStatusCode){
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
