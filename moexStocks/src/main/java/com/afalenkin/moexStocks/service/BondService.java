package com.afalenkin.moexStocks.service;

import com.afalenkin.moexStocks.dto.Stock;
import com.afalenkin.moexStocks.dto.StockPrice;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface BondService {
    List<Stock> getBonds(List<String> tickers);

    List<StockPrice> getPrices(List<String> figiList);
}
