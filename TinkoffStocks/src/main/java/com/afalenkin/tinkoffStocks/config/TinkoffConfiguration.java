package com.afalenkin.tinkoffStocks.config;

import com.afalenkin.tinkoffStocks.properties.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Configuration
@EnableConfigurationProperties(ApiProperties.class)
@RequiredArgsConstructor
public class TinkoffConfiguration {

    private final ApiProperties apiProperties;

    @Bean
    public InvestApi investApi() {
        String token = System.getenv("ssoToken");
        return apiProperties.getSandboxMode()
                ? InvestApi.createSandbox(token)
                : InvestApi.createReadonly(token);
    }
}
