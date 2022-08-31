package com.coin.coinchange.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetChangeResponse {
    private String name;
    private String value;
    private int quantity;

}
