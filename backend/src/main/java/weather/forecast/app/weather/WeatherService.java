package weather.forecast.app.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import weather.forecast.app.api.WeatherApiFacade;
import weather.forecast.app.historical.SearchLogFacade;
import weather.forecast.app.user.User;
import weather.forecast.app.weather.dto.CoordinatesDto;
import weather.forecast.app.weather.dto.DailyForecastDto;
import weather.forecast.app.weather.dto.WeeklySummaryDto;

import java.util.List;

@Service
@RequiredArgsConstructor
class WeatherService {
    private final WeatherApiFacade apiClient;
    private final WeatherCalculator calculator;
    private final ForecastBuilder forecastBuilder;

    public List<DailyForecastDto> getWeatherThisWeek(CoordinatesDto coords) {
        var nextWeekWeather = apiClient.fetchWeatherForForecast(coords);

        if (nextWeekWeather == null || nextWeekWeather.getDaily() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Data from API is null");
        }

        return forecastBuilder.toDailyForecastDtoList(nextWeekWeather);
    }

    public WeeklySummaryDto getWeeklySummary(CoordinatesDto coords){
        var nextWeekWeather = apiClient.fetchWeatherForSummary(coords);

        if (nextWeekWeather == null || nextWeekWeather.getDaily() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data from API is null");
        }

        return calculator.calculateSummary(nextWeekWeather);
    }
}
