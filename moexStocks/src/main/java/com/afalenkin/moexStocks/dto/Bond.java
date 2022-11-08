package com.afalenkin.moexStocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Data
@Builder
@AllArgsConstructor
public class Bond {
    String ticker;
    String name;
    BigDecimal price;
}
