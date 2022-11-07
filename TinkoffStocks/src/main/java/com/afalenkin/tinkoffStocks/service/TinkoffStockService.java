package com.afalenkin.tinkoffStocks.service;

import com.afalenkin.tinkoffStocks.dto.Stock;
import com.afalenkin.tinkoffStocks.dto.StockPrice;
import com.afalenkin.tinkoffStocks.exception.PriceNotFoundException;
import com.afalenkin.tinkoffStocks.exception.StockNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Quotation;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TinkoffStockService implements StockService {

    public static final String SOURCE_CODE = "TINKOFF";
    private final InvestApi investApi;

    @Override
    public Stock getStockByTicker(String ticker) {
        log.debug("Get stock {} from TINKOFF", ticker);
        List<InstrumentShort> instruments = instrumentService().findInstrumentSync(ticker);

        if (instruments.isEmpty()) {
            throw new StockNotFoundException(String.format("Instrument with ticker %s not found!", ticker));
        }

        InstrumentShort stock = instruments.get(0);
        return buildStock(stock);
    }

    @Override
    public List<Stock> getStocksByTickers(List<String> tickers) {
        log.debug("Get {} stocks from TINKOFF", tickers.size());
        List<CompletableFuture<List<InstrumentShort>>> stocks = tickers.stream()
                .peek(ticker -> log.debug("Get stock {} from TINKOFF", ticker))
                .map(instrumentService()::findInstrument)
                .toList();

        return stocks.stream()
                .map(CompletableFuture::join)
                .map(stockList -> stockList.isEmpty() ? null : stockList.get(0))
                .filter(Objects::nonNull)
                .map(this::buildStock)
                .toList();
    }

    @Override
    public StockPrice getStockPrice(String figi) {
        log.debug("Get stock price by figi {} from TINKOFF", figi);
        LastPrice lastPrice = marketDataService()
                .getLastPrices(List.of(figi))
                .join()
                .get(0);

        return getStockPrice(lastPrice);
    }

    @Override
    public List<StockPrice> getStocksPrices(List<String> figis) {
        log.debug("Get {} stocks prices from TINKOFF", figis.size());
        List<LastPrice> lastPrices = marketDataService().getLastPrices(figis).join();

        return lastPrices.stream()
                .map(this::getStockPrice)
                .toList();
    }

    private StockPrice getStockPrice(LastPrice lastPrice) {
        return Optional.ofNullable(lastPrice)
                .map(LastPrice::getPrice)
                .map(this::extractStockPrice)
                .map(price -> StockPrice.builder()
                        .figi(lastPrice.getFigi())
                        .price(price)
                        .build())
                .orElseThrow(() -> new PriceNotFoundException("Price not found!"));
    }

    private BigDecimal extractStockPrice(Quotation closePrice) {

        String price = String.valueOf(closePrice.getUnits())
                .concat(".")
                .concat(String.valueOf(closePrice.getNano()));

        return new BigDecimal(price);
    }

    @NotNull
    private MarketDataService marketDataService() {
        return investApi.getMarketDataService();
    }

    @NotNull
    private InstrumentsService instrumentService() {
        return investApi.getInstrumentsService();
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
}
