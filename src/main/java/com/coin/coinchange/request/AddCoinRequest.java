package com.coin.coinchange.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@Builder
public class AddCoinRequest {
    @Pattern(regexp = "^.(01|05|10|25)$", message = "Not a Valid Coin")
    private String value;
    @Positive(message = "Not a Valid quantity")
    private int quantity;
}
