package com.afalenkin.moexStocks.service;

import com.afalenkin.moexStocks.dto.Bond;
import com.afalenkin.moexStocks.dto.Currency;
import com.afalenkin.moexStocks.dto.Stock;
import com.afalenkin.moexStocks.exception.MoexResponseException;
import com.afalenkin.moexStocks.feign.CorporateApiClient;
import com.afalenkin.moexStocks.feign.GovernmentBondsClient;
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

    public static final String MOEX = "MOEX";
    private final CorporateApiClient corporateApiClient;
    private final GovernmentBondsClient governmentBondsClient;
    private final BondParser bondParser;

    @Override
    public List<Stock> getBonds(List<String> tickers) {
        return Stream.concat(getCorporateBonds().stream(), getGovBonds().stream())
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

    private List<Bond> getCorporateBonds() {
        log.info("Get corporate bonds from MOEX");
        List<Bond> bonds = bondParser.parse(corporateApiClient.getBonds());
        return bonds;
    }

    private List<Bond> getGovBonds() {
        log.info("Get government bonds from MOEX");
        List<Bond> bonds = bondParser.parse(governmentBondsClient.getBonds());
        checkBonds(bonds);
        return bonds;
    }

    private void checkBonds(List<Bond> bonds) {
        if (bonds.isEmpty()) {
            throw new MoexResponseException("Moex requests limit exceeded!");
        }
    }
}
