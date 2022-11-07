package com.afalenkin.tinkoffStocks.controllers;

import com.afalenkin.tinkoffStocks.dto.Stock;
import com.afalenkin.tinkoffStocks.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    @GetMapping("/{ticker}")
    public Stock getStockInfo(@PathVariable("ticker") String ticker) {
        return stockService.getStockByTicker(ticker);
    }
}
