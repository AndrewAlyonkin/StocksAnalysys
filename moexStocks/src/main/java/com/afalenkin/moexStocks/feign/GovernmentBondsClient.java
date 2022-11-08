package com.afalenkin.moexStocks.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@FeignClient(name = "corporate", url = "${moex.bonds.government}", configuration = FeignConfig.class)
public interface GovernmentBondsClient {
    @GetMapping
    String getBonds();
}
