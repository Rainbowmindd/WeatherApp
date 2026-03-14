package weather.forecast.app.weather.dto;

import lombok.Data;

import java.util.List;

@Data
public class HourlyWeatherDto {
    private List<String> time;
    private List<Double> pressure_msl;
}
