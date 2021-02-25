package com.inshur.forecast.client;

import com.inshur.forecast.config.WeatherClientConfig;
import com.inshur.forecast.dto.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="WeatherClient", url = "${weather.api.url}", configuration = WeatherClientConfig.class)
public interface WeatherClient {
    @RequestMapping(method = RequestMethod.GET, value = "/onecall", consumes = "application/json", produces = "application/json")
    WeatherResponse getForecast(@RequestParam float lat, @RequestParam float lon, @RequestParam String exclude, @RequestParam String appid);
}
