package com.afalenkin.moexStocks.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Data
public class StockPriceRequest {
    private List<String> figiList;
}
