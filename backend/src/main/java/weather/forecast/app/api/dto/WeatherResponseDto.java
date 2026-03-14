package weather.forecast.app.api.dto;

import lombok.Data;
import weather.forecast.app.weather.dto.DailyWeatherDto;
import weather.forecast.app.weather.dto.HourlyWeatherDto;

@Data
public class WeatherResponseDto {
    private double latitude;
    private double longitude;
    private DailyWeatherDto daily;
    private HourlyWeatherDto hourly;
}
