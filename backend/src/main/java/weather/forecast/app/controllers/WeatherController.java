package weather.forecast.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import weather.forecast.app.DTO.DailyForecastDTO;
import weather.forecast.app.DTO.WeeklySummaryDTO;
import weather.forecast.app.services.WeatherService;

import java.util.List;

@RestController
@RequestMapping("/v1/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(final WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/today")
    public ResponseEntity<?> getWeatherThisWeek(@RequestParam(name = "lat") double lat, @RequestParam(name = "lon") double lon){
        if (lat < -90 || lat > 90) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid latitude: must be between -90 and 90");
        }
        if (lon < -180 || lon > 180) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid longitude: must be between -180 and 180");
        }

        try {
            List<DailyForecastDTO> forecast = weatherService.getWeatherThisWeek(lat, lon);
            return ResponseEntity.ok(forecast);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @GetMapping("/weekly-summary")
    public ResponseEntity<?> getWeeklySummary(@RequestParam(name = "lat") double lat, @RequestParam(name = "lon") double lon) {
        if (lat < -90 || lat > 90) {
            //return ResponseEntity.badRequest().body("Invalid latitude: must be between -90 and 90");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid latitude: must be between -90 and 90");
        }
        if (lon < -180 || lon > 180) {
            //return ResponseEntity.badRequest().body("Invalid longitude: must be between -180 and 180");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid longitude: must be between -180 and 180");
        }

        try {
            WeeklySummaryDTO summary = weatherService.getWeeklySummary(lat, lon);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
