package com.afalenkin.tinkoffStocks.service;

import com.afalenkin.tinkoffStocks.dto.Stock;
import com.afalenkin.tinkoffStocks.dto.StockPrice;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface StockService {

    /**
     * Get info about stock from exchange
     * @param ticker - the stock ticker
     * @return - stock info
     */
    Stock getStockByTicker(String ticker);

    List<Stock> getStocksByTickers(List<String> tickers);

    StockPrice getStockPrice(String figi);

    List<StockPrice> getStocksPrices(List<String> figis);
}
