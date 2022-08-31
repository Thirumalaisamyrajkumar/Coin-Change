package com.coin.coinchange.service;

import com.coin.coinchange.model.Coin;
import com.coin.coinchange.request.AddCoinRequest;
import com.coin.coinchange.response.GetChangeResponse;

import java.util.List;

public interface CoinChangeService {
    List<Coin> getAvailableCoins();

    void addCoins(AddCoinRequest request);

    List<GetChangeResponse> getChange(int amount);
}
