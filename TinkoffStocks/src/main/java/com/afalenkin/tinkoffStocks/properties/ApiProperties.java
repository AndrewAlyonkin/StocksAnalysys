package com.afalenkin.tinkoffStocks.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Data
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    /**
     * Start application in sandbox mode for tinkoff API
     */
    private Boolean sandboxMode;
}
