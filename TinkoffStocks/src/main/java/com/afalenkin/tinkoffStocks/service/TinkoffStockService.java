package com.afalenkin.tinkoffStocks.service;

import com.afalenkin.tinkoffStocks.dto.Stock;
import com.afalenkin.tinkoffStocks.dto.StockPrice;
import com.afalenkin.tinkoffStocks.exception.StockNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.GetOrderBookResponse;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.contract.v1.Quotation;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

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
        List<InstrumentShort> instruments = instrumentService().findInstrumentSync(ticker);

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
        List<CompletableFuture<List<InstrumentShort>>> stocks = tickers.stream()
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
        GetOrderBookResponse orderBook = marketDataService().getOrderBookSync(figi, 0);

        return extractStockPrice(orderBook);
    }

    @Override
    public List<StockPrice> getStocksPrices(List<String> figis) {
        List<CompletableFuture<GetOrderBookResponse>> prices = figis.stream()
                .map(figi -> marketDataService().getOrderBook(figi, 0))
                .toList();

        return prices.stream()
                .map(CompletableFuture::join)
                .map(this::extractStockPrice)
                .toList();
    }

    private StockPrice extractStockPrice(GetOrderBookResponse orderBook) {
        Quotation closePrice = orderBook.getClosePrice();

        String price = String.valueOf(closePrice.getUnits())
                .concat(".")
                .concat(String.valueOf(closePrice.getNano()));

        return StockPrice.builder()
                .figi(orderBook.getFigi())
                .price(new BigDecimal(price))
                .build();
    }

    @NotNull
    private MarketDataService marketDataService() {
        return investApi.getMarketDataService();
    }

    @NotNull
    private InstrumentsService instrumentService() {
        return investApi.getInstrumentsService();
    }
}
