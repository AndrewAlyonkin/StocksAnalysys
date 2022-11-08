package com.afalenkin.moexStocks.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@FeignClient(name = "corporate", url = "${moex.bonds.corporate}", configuration = FeignConfig.class)
public interface CorporateApiClient {
    @GetMapping
    String getBonds();
}
