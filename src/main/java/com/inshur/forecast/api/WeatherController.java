package com.inshur.forecast.api;

import com.inshur.forecast.dto.HottestDay;
import com.inshur.forecast.exception.NoForecastAvailableException;
import com.inshur.forecast.manager.WeatherManager;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.UnknownHostException;
import java.util.Collections;

@RestController
@Slf4j
public class WeatherController {
    private final WeatherManager weatherManager;

    public WeatherController(final WeatherManager weatherManager) {
        this.weatherManager = weatherManager;
    }

    @GetMapping (value = "/forecasts")
    public HottestDay getForecast(@RequestParam final float longitude, @RequestParam final float latitude)
            throws NoForecastAvailableException {
        return weatherManager.getForecast(longitude, latitude);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            FeignException.FeignClientException.class,
            NoForecastAvailableException.class,
            UnknownHostException.class}
            )
    public ResponseEntity handleException(final Exception e) {
        if (e instanceof UnknownHostException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", e.getMessage()));
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
