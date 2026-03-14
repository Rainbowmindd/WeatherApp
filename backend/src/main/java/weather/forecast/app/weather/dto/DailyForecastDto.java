package weather.forecast.app.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyForecastDto {
    private String date;
    private int weatherCode;
    private double minTemperature;
    private double maxTemperature;
    private double estimatedEnergyKWh;
}
