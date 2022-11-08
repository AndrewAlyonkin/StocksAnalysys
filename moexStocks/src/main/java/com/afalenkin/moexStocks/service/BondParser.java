package com.afalenkin.moexStocks.service;

import com.afalenkin.moexStocks.dto.Bond;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface BondParser {
    List<Bond> parse(String xml);
}
