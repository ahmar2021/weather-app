package com.inshur.forecast;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnErrorOnNoQueryParams() throws Exception {
        this.mockMvc.perform(get("/forecasts"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnErrorOnNoLongitude() throws Exception {
        this.mockMvc.perform(get("/forecasts").queryParam("longitude", "20"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnErrorOnNoLatitude() throws Exception {
        this.mockMvc.perform(get("/forecasts").queryParam("latitude", "10"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotReturnError() throws Exception {
        this.mockMvc.perform(get("/forecasts").param("latitude", "10").param("longitude", "20"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
