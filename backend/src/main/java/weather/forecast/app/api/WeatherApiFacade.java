package weather.forecast.app.api;

import weather.forecast.app.api.dto.WeatherResponseDto;
import weather.forecast.app.weather.dto.CoordinatesDto;

public interface WeatherApiFacade {
    WeatherResponseDto fetchWeatherForForecast(CoordinatesDto coords);

    WeatherResponseDto fetchWeatherForSummary(CoordinatesDto coords);
}
