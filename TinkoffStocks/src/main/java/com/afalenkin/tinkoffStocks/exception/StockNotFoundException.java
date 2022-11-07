package com.afalenkin.tinkoffStocks.exception;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String message) {
        super(message);
    }
}
