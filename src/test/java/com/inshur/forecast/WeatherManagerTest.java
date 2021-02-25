package com.inshur.forecast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inshur.forecast.dto.HottestDay;
import com.inshur.forecast.dto.WeatherResponse;
import com.inshur.forecast.exception.NoForecastAvailableException;
import com.inshur.forecast.manager.WeatherManager;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WeatherAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherManagerTest {

    @Autowired
    private WeatherManager manager;

    private ObjectMapper mapper;

    @BeforeAll
    public final void setUp() {
        this.mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    @Test
    public void testSingleMax() throws Exception  {
        final String responseJson = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sample_response.json"));
        final WeatherResponse response = mapper.readValue(responseJson, WeatherResponse.class);
        final HottestDay hottestDay = manager.getHottestDay(response);
        Assertions.assertEquals(1614535200, hottestDay.getDt());
        Assertions.assertEquals(293.48F, hottestDay.getTemperature());
        Assertions.assertEquals(93, hottestDay.getHumidity());
    }

    @Test
    public void testMultipleMaxesWithLowHumidity() throws Exception  {
        final String responseJson = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sample_response_humidity.json"));
        final WeatherResponse response = mapper.readValue(responseJson, WeatherResponse.class);
        final HottestDay hottestDay = manager.getHottestDay(response);
        Assertions.assertEquals(1614708000, hottestDay.getDt());
        Assertions.assertEquals(293.48F, hottestDay.getTemperature());
        Assertions.assertEquals(86, hottestDay.getHumidity());
    }

    @Test
    public void testMultipleMaxesWithSameHumidity() throws Exception  {
        final String responseJson = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sample_response_same_max_and_humidity.json"));
        final WeatherResponse response = mapper.readValue(responseJson, WeatherResponse.class);
        final HottestDay hottestDay = manager.getHottestDay(response);
        Assertions.assertEquals(1614535200, hottestDay.getDt());
        Assertions.assertEquals(293.48F, hottestDay.getTemperature());
        Assertions.assertEquals(86, hottestDay.getHumidity());
    }

    @Test
    public void testNoForecastAvailable() throws Exception  {
        final String responseJson = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sample_response_no_results.json"));
        final WeatherResponse response = mapper.readValue(responseJson, WeatherResponse.class);
        Assertions.assertThrows(NoForecastAvailableException.class, () -> manager.getHottestDay(response));
    }
}
