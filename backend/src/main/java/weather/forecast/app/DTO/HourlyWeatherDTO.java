package weather.forecast.app.DTO;

import java.util.List;

public class HourlyWeatherDTO {
    private List<String> time;
    private List<Double> pressure_msl;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Double> getPressure_msl() {
        return pressure_msl;
    }

    public void setPressure_msl(List<Double> pressure_msl) {
        this.pressure_msl = pressure_msl;
    }
}
