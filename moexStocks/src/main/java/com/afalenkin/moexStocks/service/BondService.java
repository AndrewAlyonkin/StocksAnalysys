package com.afalenkin.moexStocks.service;

import com.afalenkin.moexStocks.dto.Stock;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface BondService {
    List<Stock> getBonds(List<String> tickers);
}
