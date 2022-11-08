package com.afalenkin.moexStocks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Data
@AllArgsConstructor
public class StockPricesResponse {
    private List<StockPrice> stockPrices;
}
