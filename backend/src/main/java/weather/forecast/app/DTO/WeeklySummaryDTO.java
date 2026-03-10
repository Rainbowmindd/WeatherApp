package weather.forecast.app.DTO;

public class WeeklySummaryDTO {
    private double averagePressure;
    private double averageSunshineHours;
    private double minTemperature;
    private double maxTemperature;
    private String weatherSummary;

    public double getAveragePressure() {
        return averagePressure;
    }

    public void setAveragePressure(double averagePressure) {
        this.averagePressure = averagePressure;
    }

    public double getAverageSunshineHours() {
        return averageSunshineHours;
    }

    public void setAverageSunshineHours(double averageSunshineHours) {
        this.averageSunshineHours = averageSunshineHours;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getWeatherSummary() {
        return weatherSummary;
    }

    public void setWeatherSummary(String weatherSummary) {
        this.weatherSummary = weatherSummary;
    }
}
