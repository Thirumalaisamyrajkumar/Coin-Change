package com.coin.coinchange.service.impl;

import com.coin.coinchange.model.Coin;
import com.coin.coinchange.request.AddCoinRequest;
import com.coin.coinchange.response.GetChangeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CoinChangeServiceImpltest {
    @InjectMocks
    private CoinChangeServiceImpl coinChangeService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        Coin cent = Coin.builder().value(1).count(100).name("Cent").build();
        Coin nickel = Coin.builder().value(5).count(100).name("Nickel").build();
        Coin dime = Coin.builder().value(10).count(100).name("Dime").build();
        Coin quarter = Coin.builder().value(25).count(100).name("Quarter").build();
        ReflectionTestUtils.setField(coinChangeService,"availableCoins", List.of(cent,nickel,dime,quarter));
    }

    @Test
    public void testGetCoinChange(){
        List<GetChangeResponse> response = coinChangeService.getChange(30);
        assertNotNull(response);
        assertEquals(2,response.size());
        assertEquals(100, response.get(1).getQuantity());
        assertEquals(50, response.get(0).getQuantity());
    }

    @Test
    public void testAddCoins(){
        coinChangeService.addCoins(AddCoinRequest.builder().value(".01").quantity(10).build());
        List<Coin> coins = (List<Coin>) ReflectionTestUtils.getField(coinChangeService,"availableCoins");
        assertNotNull(coins);
        assertEquals(110, coins.get(0).getCount());
    }

}
