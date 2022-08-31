package com.coin.coinchange.controller;

import com.coin.coinchange.model.Coin;
import com.coin.coinchange.request.AddCoinRequest;
import com.coin.coinchange.request.GetChangeRequest;
import com.coin.coinchange.response.GetChangeResponse;
import com.coin.coinchange.service.CoinChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CoinChangeController {
    @Autowired
    CoinChangeService service;

    @GetMapping(value ="/available/coins", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<Coin> getAvailableCoins(){
        return service.getAvailableCoins();
    }

    @PutMapping(value = "add/coin", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void addCoins(@RequestBody @Valid AddCoinRequest request){

        service.addCoins(request);
    }

    @PostMapping(value="coin/change", consumes = {"application/json"})
    public ResponseEntity<List<GetChangeResponse>> getChange(@RequestBody GetChangeRequest request){
        return ResponseEntity.ok().body(service.getChange(request.getAmount()));
    }
}
