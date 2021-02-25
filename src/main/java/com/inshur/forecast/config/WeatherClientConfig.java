package com.inshur.forecast.config;

import com.inshur.forecast.client.WeatherClient;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

@EnableFeignClients(basePackageClasses = {
        WeatherClient.class
})
@Configuration
public class WeatherClientConfig {
    @Bean
    Retryer retryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }
}
