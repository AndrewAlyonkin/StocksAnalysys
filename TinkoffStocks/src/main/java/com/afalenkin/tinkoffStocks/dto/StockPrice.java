package com.afalenkin.tinkoffStocks.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Data
@Builder
public class StockPrice {
    String figi;
    BigDecimal price;
}
