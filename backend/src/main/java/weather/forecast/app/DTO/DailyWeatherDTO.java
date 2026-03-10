package weather.forecast.app.DTO;

import java.util.List;

public class DailyWeatherDTO {
    private List<String> time;
    private List<Integer> weathercode;
    private List<Double> temperature_2m_max;
    private List<Double> temperature_2m_min;
    private List<Double> sunshine_duration;
    private List<Double> precipitation_sum;
    private List<Double> pressure_msl;

    //Getters and setters
    public List<Double> getPressure_msl() {
        return pressure_msl;
    }

    public void setPressure_msl(List<Double> pressure_msl) {
        this.pressure_msl = pressure_msl;
    }

    public List<Double> getPrecipitation_sum() {
        return precipitation_sum;
    }

    public void setPrecipitation_sum(List<Double> precipitation_sum) {
        this.precipitation_sum = precipitation_sum;
    }

    public List<Double> getSunshine_duration() {
        return sunshine_duration;
    }

    public void setSunshine_duration(List<Double> sunshine_duration) {
        this.sunshine_duration = sunshine_duration;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Integer> getWeathercode() {
        return weathercode;
    }

    public void setWeathercode(List<Integer> weathercode) {
        this.weathercode = weathercode;
    }

    public List<Double> getTemperature_2m_max() {
        return temperature_2m_max;
    }

    public void setTemperature_2m_max(List<Double> temperature_2m_max) {
        this.temperature_2m_max = temperature_2m_max;
    }

    public List<Double> getTemperature_2m_min() {
        return temperature_2m_min;
    }

    public void setTemperature_2m_min(List<Double> temperature_2m_min) {
        this.temperature_2m_min = temperature_2m_min;
    }
}
