package com.afalenkin.tinkoffStocks.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Data
public class StocksResponse {
    private final List<Stock> stocks;
}
