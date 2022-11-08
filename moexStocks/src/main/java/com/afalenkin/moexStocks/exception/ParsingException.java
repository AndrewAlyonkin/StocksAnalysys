package com.afalenkin.moexStocks.exception;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class ParsingException extends RuntimeException {
    public ParsingException(Exception ex) {
        super(ex);
    }
}
