package weather.forecast.app.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeeklySummaryDto {
    private double averagePressure;
    private double averageSunshineHours;
    private double minTemperature;
    private double maxTemperature;
    private String weatherSummary;
}
