package weather.forecast.app.DTO;

public class WeatherResponseDTO {
    private double latitude;
    private double longitude;
    private DailyWeatherDTO daily;
    private HourlyWeatherDTO hourly;

    //getters and setters
    public HourlyWeatherDTO getHourly() {
        return hourly;
    }

    public void setHourly(HourlyWeatherDTO hourly) {
        this.hourly = hourly;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public DailyWeatherDTO getDaily() {
        return daily;
    }

    public void setDaily(DailyWeatherDTO daily) {
        this.daily = daily;
    }
}
