package com.afalenkin.tinkoffStocks.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Data
public class TickerRequest {
    private List<String> tickers;
}
