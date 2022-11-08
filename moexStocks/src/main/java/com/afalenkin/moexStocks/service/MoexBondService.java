package com.afalenkin.moexStocks.service;

import com.afalenkin.moexStocks.dto.Bond;
import com.afalenkin.moexStocks.dto.Currency;
import com.afalenkin.moexStocks.dto.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MoexBondService implements BondService {

    private final BondRequester bondRequester;
    private static final String MOEX = "MOEX";

    @Override
    public List<Stock> getBonds(List<String> tickers) {
        return Stream.concat(bondRequester.getCorporateBonds().stream(),
                        bondRequester.getGovBonds().stream())
                .filter(bond -> tickers.contains(bond.getTicker()))
                .map(this::mapToStock)
                .toList();
    }

    private Stock mapToStock(Bond bond) {
        return Stock.builder()
                .currency(Currency.RUB)
                .figi(bond.getTicker())
                .name(bond.getName())
                .source(MOEX)
                .ticker(bond.getTicker())
                .type("bond")
                .build();
    }
}
