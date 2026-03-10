package weather.forecast.app.DTO;

public class DailyForecastDTO {
    private String date;
    private int weatherCode;
    private double minTemperature;
    private double maxTemperature;
    private double estimatedEnergyKWh;

    public DailyForecastDTO() {}

    public DailyForecastDTO(String date, int weatherCode, double minTemperature, double maxTemperature, double estimatedEnergyKWh) {
        this.date = date;
        this.weatherCode = weatherCode;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.estimatedEnergyKWh = estimatedEnergyKWh;
    }

    // Getters and setters

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getWeatherCode() { return weatherCode; }
    public void setWeatherCode(int weatherCode) { this.weatherCode = weatherCode; }

    public double getMinTemperature() { return minTemperature; }
    public void setMinTemperature(double minTemperature) { this.minTemperature = minTemperature; }

    public double getMaxTemperature() { return maxTemperature; }
    public void setMaxTemperature(double maxTemperature) { this.maxTemperature = maxTemperature; }

    public double getEstimatedEnergyKWh() { return estimatedEnergyKWh; }
    public void setEstimatedEnergyKWh(double estimatedEnergyKWh) { this.estimatedEnergyKWh = estimatedEnergyKWh; }
}
