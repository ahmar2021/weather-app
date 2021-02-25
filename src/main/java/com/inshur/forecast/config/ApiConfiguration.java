package com.inshur.forecast.config;

import com.inshur.forecast.client.WeatherClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackageClasses = {
        WeatherClient.class
})
@Configuration
@ConfigurationProperties(prefix = "weather.api")
@Getter
@Setter
public class ApiConfiguration {
    String key;
    String exclude;
    int days;
}
