package com.afalenkin.tinkoffStocks.service;

import com.afalenkin.tinkoffStocks.dto.Stock;
import com.afalenkin.tinkoffStocks.exception.StockNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
@RequiredArgsConstructor
public class TinkoffStockService implements StockService {

    public static final String SOURCE_CODE = "TINKOFF";
    private final InvestApi investApi;

    @Override
    public Stock getStockByTicker(String ticker) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        List<InstrumentShort> instruments = instrumentsService.findInstrumentSync(ticker);

        if (instruments.isEmpty()) {
            throw new StockNotFoundException(String.format("Instrument with ticker %s not found!", ticker));
        }

        InstrumentShort stock = instruments.get(0);
        return Stock.builder()
                .figi(stock.getFigi())
                .name(stock.getName())
                .source(SOURCE_CODE)
                .ticker(stock.getTicker())
                .type(stock.getInstrumentType())
                .build();
    }
}
