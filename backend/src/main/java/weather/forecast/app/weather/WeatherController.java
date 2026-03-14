package weather.forecast.app.weather;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import weather.forecast.app.weather.dto.CoordinatesDto;
import weather.forecast.app.weather.dto.DailyForecastDto;
import weather.forecast.app.weather.dto.WeeklySummaryDto;

import java.util.List;

@RestController
@RequestMapping("/v1/weather")
class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(final WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/today")
    public ResponseEntity<?> getWeatherThisWeek(@RequestParam(name = "lat") Double lat, @RequestParam(name = "lon") Double lon){
        try {
            CoordinatesDto coords = new CoordinatesDto(lat, lon);
            List<DailyForecastDto> forecast = weatherService.getWeatherThisWeek(coords);
            return ResponseEntity.ok(forecast);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @GetMapping("/weekly-summary")
    public ResponseEntity<?> getWeeklySummary(@RequestParam(name = "lat") Double lat, @RequestParam(name = "lon") Double lon) {
        try {
            CoordinatesDto coords = new CoordinatesDto(lat, lon);
            WeeklySummaryDto summary = weatherService.getWeeklySummary(coords);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
