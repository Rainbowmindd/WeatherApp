package weather.forecast.app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import weather.forecast.app.DTO.DailyForecastDTO;
import weather.forecast.app.DTO.WeatherResponseDTO;
import weather.forecast.app.DTO.WeeklySummaryDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    private static final double PANEL_CAPACITY_KW = 2.5;
    private static final double PANEL_EFFICIENCY = 0.2;

    private final WeatherApiClient apiClient;
    private final WeatherCalculator calculator;
    private final ForecastBuilder forecastBuilder;

    public WeatherService(WeatherApiClient apiClient, WeatherCalculator calculator, ForecastBuilder forecastBuilder) {
        this.calculator = calculator;
        this.apiClient = apiClient;
        this.forecastBuilder = forecastBuilder;
    }

    public List<DailyForecastDTO> getWeatherThisWeek(double lat, double lon) {
        WeatherResponseDTO nextWeekWeather = apiClient.fetchWeatherForForecast(lat, lon);

        if (nextWeekWeather == null || nextWeekWeather.getDaily() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Data from API is null");
        }

        return forecastBuilder.build(nextWeekWeather);
    }

    public WeeklySummaryDTO getWeeklySummary(double lat, double lon){
        WeatherResponseDTO nextWeekWeather = apiClient.fetchWeatherForSummary(lat,lon);

        if (nextWeekWeather == null || nextWeekWeather.getDaily() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data from API is null");
        }

        return calculator.calculateSummary(nextWeekWeather);
    }
}
