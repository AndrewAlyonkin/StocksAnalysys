package com.afalenkin.tinkoffStocks.exception;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message) {
        super(message);
    }
}
