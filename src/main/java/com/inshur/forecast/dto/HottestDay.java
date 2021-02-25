package com.inshur.forecast.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Builder
@Data
public class HottestDay {
    final long dt;
    final Date date;
    final float temperature;
    final int humidity;
}
