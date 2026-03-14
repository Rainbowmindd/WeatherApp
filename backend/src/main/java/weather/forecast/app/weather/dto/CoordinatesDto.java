package weather.forecast.app.weather.dto;

import lombok.Data;

@Data
public class CoordinatesDto {
    private final Double lat;
    private final Double lon;

    public CoordinatesDto(Double lat, Double lon) {
        this.lat = ((lat + 90) % 180 + 180) % 180 - 90;
        this.lon = ((lon + 180) % 360 + 360) % 360 - 180;
    }
}
