package weather.forecast.app.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import weather.forecast.app.DTO.WeatherResponseDTO;
import weather.forecast.app.DTO.WeeklySummaryDTO;

import java.util.List;

@Component
public class WeatherCalculator {

    public WeeklySummaryDTO calculateSummary(WeatherResponseDTO weather) {
        List<Double> pressures = weather.getHourly().getPressure_msl();
        List<Double> sunshineDurations = weather.getDaily().getSunshine_duration();
        List<Double> tempsMin = weather.getDaily().getTemperature_2m_min();
        List<Double> tempsMax = weather.getDaily().getTemperature_2m_max();
        List<Double> precipitationSums = weather.getDaily().getPrecipitation_sum();

        if (sunshineDurations.size()!=7 || tempsMin.size()!=7 || tempsMax.size()!=7 || precipitationSums.size()!=7 || pressures.size()!=7*24) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Data received from API is incomplete!");
        }

        double sumPressure = 0.0;
        double sumSunshine = 0.0;
        double minTemp = Double.MAX_VALUE;
        double maxTemp = -Double.MAX_VALUE;
        int rainyDaysCount = 0;

        for (int i = 0; i < 7; i++) {
            sumSunshine += sunshineDurations.get(i);

            if (tempsMin.get(i) < minTemp) minTemp = tempsMin.get(i);
            if (tempsMax.get(i) > maxTemp) maxTemp = tempsMax.get(i);

            if (precipitationSums.get(i) != null && precipitationSums.get(i) > 0.0) {
                rainyDaysCount++;
            }
        }

        for (int i = 0; i<24*7; i++) {
            sumPressure += pressures.get(i);
        }

        double avgPressure = sumPressure/(24*7);
        double avgSunshineHours = sumSunshine/3600/7;

        String weatherSummary = (rainyDaysCount >= 4) ? "rainfall" : "no rainfall";
        WeeklySummaryDTO summary = new WeeklySummaryDTO();
        summary.setAveragePressure(avgPressure);
        summary.setAverageSunshineHours(avgSunshineHours);
        summary.setMinTemperature(minTemp);
        summary.setMaxTemperature(maxTemp);
        summary.setWeatherSummary(weatherSummary);
        return summary;
    }
}
