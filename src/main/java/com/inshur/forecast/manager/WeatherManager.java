package com.inshur.forecast.manager;

import com.inshur.forecast.client.WeatherClient;
import com.inshur.forecast.config.ApiConfiguration;
import com.inshur.forecast.dto.DayForecast;
import com.inshur.forecast.dto.HottestDay;
import com.inshur.forecast.dto.WeatherResponse;
import com.inshur.forecast.exception.NoForecastAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeatherManager {
    private final WeatherClient weatherClient;
    private final ApiConfiguration config;

    public WeatherManager(final WeatherClient weatherClient, final ApiConfiguration config) {
        this.weatherClient = weatherClient;
        this.config = config;
    }

    public HottestDay getForecast(final float longitude, final float latitude) throws NoForecastAvailableException {
        WeatherResponse response = weatherClient.getForecast(latitude, longitude, config.getExclude(), config.getKey());
        return getHottestDay(response);
    }

    public HottestDay getHottestDay(final WeatherResponse response) throws NoForecastAvailableException {
        final List<DayForecast> dailyForecasts = response.getDaily();
        if (dailyForecasts != null && !dailyForecasts.isEmpty()) {
            final float maxTemperature = response.getDaily().stream()
                    .map(a -> a.getTemp().getMax())
                    .max(Float::compareTo).get();
            final List<DayForecast> daysWithMaxTemperature = dailyForecasts.stream().
                    filter(a -> a.getTemp().getMax() == maxTemperature)
                    .collect(Collectors.toList());

            final DayForecast hottestDay;
            if (daysWithMaxTemperature.size() == 1) {
                hottestDay = daysWithMaxTemperature.stream().findFirst().get();
            } else {
                final int minHumidity = daysWithMaxTemperature.stream().map(a -> a.getHumidity()).min(Integer::compareTo).get();
                hottestDay = daysWithMaxTemperature.stream().filter(a->a.getHumidity() == minHumidity).findFirst().get();
            }
            final Date hottestDate = new Date(hottestDay.getDt()*1000);
            return HottestDay.builder()
                    .temperature(hottestDay.getTemp().getMax())
                    .humidity(hottestDay.getHumidity())
                    .dt(hottestDay.getDt())
                    .date(hottestDate)
                    .build();
        } else {
            throw new NoForecastAvailableException("No forecast available");
        }
    }
}
