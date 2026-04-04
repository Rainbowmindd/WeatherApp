package weather.forecast.app.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import weather.forecast.app.weather.dto.DailyForecastDto;
import weather.forecast.app.api.dto.WeatherResponseDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class ForecastBuilder {
    private final EnergyCalculatorService energyCalculatorService;

    public List<DailyForecastDto> toDailyForecastDtoList(WeatherResponseDto weather) {
        var dates = weather.getDaily().getTime();
        var weatherCodes = weather.getDaily().getWeathercode();
        var sunshineDurations = weather.getDaily().getSunshine_duration();
        var tempsMin = weather.getDaily().getTemperature_2m_min();
        var tempsMax = weather.getDaily().getTemperature_2m_max();

        validateData(dates, weatherCodes, sunshineDurations, tempsMin, tempsMax);

        return toForecastList(dates, weatherCodes, sunshineDurations, tempsMin, tempsMax);
    }

    List<DailyForecastDto> toForecastList(List<String> dates, List<Integer> weatherCodes, List<Double> sunshineDurations, List<Double> tempsMin, List<Double> tempsMax) {
        List<DailyForecastDto> forecastList = new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {
            // Szacujemy produkcję energii na podstawie czasu nasłonecznienia danego dnia
            Double energyProduced = energyCalculatorService.calculateEnergyKWh(sunshineDurations.get(i));
            DailyForecastDto daily = new DailyForecastDto(
                    dates.get(i),
                    weatherCodes.get(i),
                    tempsMin.get(i),
                    tempsMax.get(i),
                    energyProduced
            );
            forecastList.add(daily);
        }
        return forecastList;
    }

    void validateData(List<String> dates, List<Integer> weatherCodes, List<Double> sunshineDurations, List<Double> tempsMin, List<Double> tempsMax) {
        if (dates.size()!=7 || weatherCodes.size()!=7 || sunshineDurations.size()!=7 || tempsMin.size()!=7 || tempsMax.size()!=7) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Data from API is incomplete!");
        }
    }
}
