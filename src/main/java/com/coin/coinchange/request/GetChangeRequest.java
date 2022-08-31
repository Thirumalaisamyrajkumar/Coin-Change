package com.coin.coinchange.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class GetChangeRequest {
    //@Positive(message = "Only positive value allowed")
    private int amount;
}
