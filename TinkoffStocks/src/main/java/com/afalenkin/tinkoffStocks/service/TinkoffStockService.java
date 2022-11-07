package com.afalenkin.tinkoffStocks.service;

import com.afalenkin.tinkoffStocks.dto.Stock;
import com.afalenkin.tinkoffStocks.exception.StockNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
        return buildStock(stock);
    }

    private Stock buildStock(InstrumentShort stock) {
        return Stock.builder()
                .figi(stock.getFigi())
                .name(stock.getName())
                .source(SOURCE_CODE)
                .ticker(stock.getTicker())
                .type(stock.getInstrumentType())
                .build();
    }

    @Override
    public List<Stock> getStocksByTickers(List<String> tickers) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        List<CompletableFuture<List<InstrumentShort>>> stocks = tickers.stream()
                .map(instrumentsService::findInstrument)
                .toList();

        return stocks.stream()
                .map(CompletableFuture::join)
                .map(stockList -> stockList.isEmpty() ? null : stockList.get(0))
                .filter(Objects::nonNull)
                .map(this::buildStock)
                .toList();
    }
}
