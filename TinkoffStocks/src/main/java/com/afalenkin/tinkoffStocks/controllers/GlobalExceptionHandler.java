package com.afalenkin.tinkoffStocks.controllers;

import com.afalenkin.tinkoffStocks.dto.ErrorDto;
import com.afalenkin.tinkoffStocks.exception.StockNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({StockNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(Exception exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getLocalizedMessage()), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDto> handleGlobalException(Exception exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
}
