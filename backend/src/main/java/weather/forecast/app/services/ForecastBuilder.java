package weather.forecast.app.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import weather.forecast.app.DTO.DailyForecastDTO;
import weather.forecast.app.DTO.WeatherResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class ForecastBuilder {

    private final EnergyCalculator energyCalculator;

    public ForecastBuilder(EnergyCalculator energyCalculator) {
        this.energyCalculator = energyCalculator;
    }

    public List<DailyForecastDTO> build(WeatherResponseDTO weather){
        List<DailyForecastDTO> forecastList = new ArrayList<>();

        List<String> dates = weather.getDaily().getTime();
        List<Integer> weatherCodes = weather.getDaily().getWeathercode();
        List<Double> sunshineDurations = weather.getDaily().getSunshine_duration();
        List<Double> tempsMin = weather.getDaily().getTemperature_2m_min();
        List<Double> tempsMax = weather.getDaily().getTemperature_2m_max();

        if (dates.size()!=7 || weatherCodes.size()!=7 || sunshineDurations.size()!=7|| tempsMin.size()!=7 || tempsMax.size()!=7) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Data from API is incomplete!");
        }

        for (int i = 0; i < dates.size(); i++) {
            double energyProduced = energyCalculator.calculateEnergyKWh(sunshineDurations.get(i));

            DailyForecastDTO daily = new DailyForecastDTO(
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
}
