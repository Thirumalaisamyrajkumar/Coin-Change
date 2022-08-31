package com.coin.coinchange.service.impl;

import com.coin.coinchange.exception.NotEnoughCoinException;
import com.coin.coinchange.model.Coin;
import com.coin.coinchange.request.AddCoinRequest;
import com.coin.coinchange.response.GetChangeResponse;
import com.coin.coinchange.service.CoinChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CoinChangeServiceImpl implements CoinChangeService {
    private List<Coin> availableCoins;
    @PostConstruct
    private void setup(){
        Coin cent = Coin.builder().value(1).count(100).name("Cent").build();
        Coin nickel = Coin.builder().value(5).count(100).name("Nickel").build();
        Coin dime = Coin.builder().value(10).count(100).name("Dime").build();
        Coin quarter = Coin.builder().value(25).count(100).name("Quarter").build();

        availableCoins = List.of(cent,nickel,dime,quarter);
    }

    @Override
    public List<Coin> getAvailableCoins() {
        return availableCoins;
    }

    @Override
    public void addCoins(AddCoinRequest request) {

        Coin matchedCoin = availableCoins.stream().
                filter(coin -> coin.getValue() == Integer.valueOf(request.getValue().substring(1))).findFirst().get();
        log.info("Adding {} coins to available {}" , request.getQuantity(), matchedCoin.getName());
        matchedCoin.setCount(matchedCoin.getCount()+ request.getQuantity());
    }

    @Override
    public List<GetChangeResponse> getChange(int amount){
        log.info("Getting change for amount : {}",amount);
        amount = amount*100;
        int totalChangeAmount = 0;
        for(Coin coin: availableCoins){
            totalChangeAmount += coin.getCount()*coin.getValue();
        }
        if(amount > totalChangeAmount){
            throw new NotEnoughCoinException("No Change left", HttpStatus.BAD_REQUEST.value());
        }

        int[] change =getChangeDenominations(amount ,
                availableCoins.stream().mapToInt(Coin::getValue).toArray(),
                availableCoins.stream().mapToInt(Coin::getCount).toArray());
        List<GetChangeResponse> response = new ArrayList<>();
        for(int i=0;i<change.length;i++){
            if(change[i] !=0) {
                Coin coin = availableCoins.get(i);
                coin.setCount(coin.getCount() - change[i]);
                var changeResponse = GetChangeResponse.builder().quantity(change[i]).name(coin.getName())
                        .value("."+coin.getValue()).build();
                response.add(changeResponse);
            }
        }
        log.info("get change response : {}", response.toString());
        return response;
    }

    private int[] getChangeDenominations(int amount, int[] coins, int[] quantity)
    {
        int[][] coinsUsed = new int[amount + 1][];
        for (int i = 0; i <= amount; ++i)
        {
            coinsUsed[i] = new int[coins.length];
        }

        int[] minCoins = new int[amount + 1];
        for (int i = 1; i <= amount; ++i)
        {
            minCoins[i] = Integer.MAX_VALUE - 1;
        }

        int[] limitsCopy = new int[quantity.length];
        limitsCopy = Arrays.copyOf(quantity, quantity.length);

        for (int i = 0; i < coins.length; ++i)
        {
            while (limitsCopy[i] > 0)
            {
                for (int j = amount; j >= 0; --j)
                {
                    int currAmount = j + coins[i];
                    if (currAmount <= amount)
                    {
                        if (minCoins[currAmount] > minCoins[j] + 1)
                        {
                            minCoins[currAmount] = minCoins[j] + 1;
                            coinsUsed[currAmount]  = Arrays.copyOf(coinsUsed[j], coinsUsed[currAmount].length);
                            coinsUsed[currAmount][i] += 1;
                        }
                    }
                }

                limitsCopy[i] -= 1;
            }
        }

        return coinsUsed[amount];
    }
}
