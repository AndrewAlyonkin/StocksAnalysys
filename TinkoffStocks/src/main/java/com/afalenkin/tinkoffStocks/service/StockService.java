package com.afalenkin.tinkoffStocks.service;

import com.afalenkin.tinkoffStocks.dto.Stock;

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
}
