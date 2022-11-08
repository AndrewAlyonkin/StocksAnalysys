package com.afalenkin.moexStocks.controllers;

import com.afalenkin.moexStocks.dto.StocksResponse;
import com.afalenkin.moexStocks.dto.TickerRequest;
import com.afalenkin.moexStocks.service.BondService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@RestController
@RequestMapping("/bonds")
@RequiredArgsConstructor
public class BondController {

    private final BondService bondService;

    @PutMapping
    public StocksResponse getBonds(@RequestBody TickerRequest request){
        return new StocksResponse(bondService.getBonds(request.getTickers()));
    }
}
