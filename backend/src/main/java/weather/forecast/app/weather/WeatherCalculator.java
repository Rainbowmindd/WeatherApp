package weather.forecast.app.weather;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import weather.forecast.app.api.dto.WeatherResponseDto;
import weather.forecast.app.weather.dto.WeeklySummaryDto;

import java.util.List;

@Component
class WeatherCalculator {

    public WeeklySummaryDto calculateSummary(WeatherResponseDto weather) {
        var pressures = weather.getHourly().getPressure_msl();
        var sunshineDurations = weather.getDaily().getSunshine_duration();
        var tempsMin = weather.getDaily().getTemperature_2m_min();
        var tempsMax = weather.getDaily().getTemperature_2m_max();
        var precipitationSums = weather.getDaily().getPrecipitation_sum();

        // Dane dzienne muszą mieć 7 wartości, ciśnienie 7*24 (godzinowe przez cały tydzień)
        if (sunshineDurations.size()!=7 || tempsMin.size()!=7 || tempsMax.size()!=7 || precipitationSums.size()!=7 || pressures.size()!=7*24) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Data received from API is incomplete!");
        }

        Double sumSunshine = sunshineDurations.stream().reduce(0.0, Double::sum);
        Double sumPressure = pressures.stream().reduce(0.0, Double::sum);
        Double minTemp = tempsMin.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        Double maxTemp = tempsMax.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        Integer rainyDaysCount = (int) precipitationSums.stream().filter(d -> d>0).count();

        Double avgPressure = sumPressure/(24*7);
        Double avgSunshineHours = sumSunshine/3600/7; // API zwraca sekundy, przeliczamy na godziny

        // Tydzień uznajemy za deszczowy jeśli pada przez większość dni
        String weatherSummary = (rainyDaysCount >= 4) ? "rainfall" : "no rainfall";
        return new WeeklySummaryDto(
                avgPressure,
                avgSunshineHours,
                minTemp,
                maxTemp,
                weatherSummary
        );
    }
}
