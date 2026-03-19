package weather.forecast.app.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsResponse {
    private long totalSearches;
    private List<CityCount> topCities;
}
