package com.coin.coinchange.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private int httpStatus;
    private String error;
}
