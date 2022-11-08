package com.afalenkin.moexStocks.service;

import com.afalenkin.moexStocks.dto.Bond;
import com.afalenkin.moexStocks.exception.MoexResponseException;
import com.afalenkin.moexStocks.feign.CorporateApiClient;
import com.afalenkin.moexStocks.feign.GovernmentBondsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MexBondRequester implements BondRequester {

    private final CorporateApiClient corporateApiClient;
    private final GovernmentBondsClient governmentBondsClient;
    private final BondParser bondParser;

    @Cacheable(value = "CORPORATE_BONDS")
    public List<Bond> getCorporateBonds() {
        log.info("Get corporate bonds from MOEX");
        List<Bond> bonds = bondParser.parse(corporateApiClient.getBonds());
        return bonds;
    }

    @Cacheable(value = "GOVERNMENT_BONDS")
    public List<Bond> getGovBonds() {
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
