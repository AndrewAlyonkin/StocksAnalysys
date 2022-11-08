package com.afalenkin.moexStocks.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Data
@Builder
public class Stock {

    String ticker;
    String figi;
    String name;
    String type;
    Currency currency;
    String source;

}
