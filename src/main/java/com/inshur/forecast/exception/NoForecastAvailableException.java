package com.inshur.forecast.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "No forecast available")
public class NoForecastAvailableException extends Exception {
    public NoForecastAvailableException(final String message) {
        super(message);
    }
}
