package com.afalenkin.tinkoffStocks.controllers;

import com.afalenkin.tinkoffStocks.dto.Stock;
import com.afalenkin.tinkoffStocks.dto.StockPrice;
import com.afalenkin.tinkoffStocks.dto.StockPriceRequest;
import com.afalenkin.tinkoffStocks.dto.StockPricesResponse;
import com.afalenkin.tinkoffStocks.dto.StocksResponse;
import com.afalenkin.tinkoffStocks.dto.TickerRequest;
import com.afalenkin.tinkoffStocks.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    public Stock getStockInfo(@NotNull @PathVariable("ticker") String ticker) {
        return stockService.getStockByTicker(ticker);
    }

    @PutMapping
    public StocksResponse getStocksInfo(@NotNull @RequestBody TickerRequest tickerRequest) {
        List<String> tickers = tickerRequest.getTickers();
        if (tickers.isEmpty()) {
            throw new IllegalArgumentException("Tickers list is empty!");
        }
        return new StocksResponse(stockService.getStocksByTickers(tickers));
    }

    @GetMapping("/price/{figi}")
    public StockPrice getStockPrice(@NotNull @PathVariable("figi") String figi) {
        return stockService.getStockPrice(figi);
    }

    @PutMapping("/price")
    public StockPricesResponse getStockPrices(@NotNull @RequestBody StockPriceRequest stockPriceRequest) {
        List<String> figis = stockPriceRequest.getFigiList();
        if (figis.isEmpty()) {
            throw new IllegalArgumentException("Figis list is empty!");
        }
        return new StockPricesResponse(stockService.getStocksPrices(figis));
    }

}
