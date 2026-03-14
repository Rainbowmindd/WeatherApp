package weather.forecast.app.weather.dto;

import lombok.Data;

import java.util.List;

@Data
public class DailyWeatherDto {
    private List<String> time;
    private List<Integer> weathercode;
    private List<Double> temperature_2m_max;
    private List<Double> temperature_2m_min;
    private List<Double> sunshine_duration;
    private List<Double> precipitation_sum;
    private List<Double> pressure_msl;
}
