package com.afalenkin.moexStocks.exception;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class MoexResponseException extends RuntimeException {
    public MoexResponseException(String message) {
        super(message);
    }
}
