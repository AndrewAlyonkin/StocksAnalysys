package com.afalenkin.tinkoffStocks.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@AllArgsConstructor
@Getter
public enum Currency {
    RUB("RUB"),
    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    HKD("HKD"),
    CHF("CHF"),
    JPY("JPY"),
    CNY("CNY"),
    TRY("TRY");

    private String currency;
}
