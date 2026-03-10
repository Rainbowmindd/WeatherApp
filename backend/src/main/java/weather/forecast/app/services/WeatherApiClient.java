package weather.forecast.app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import weather.forecast.app.DTO.WeatherResponseDTO;

@Service
public class WeatherApiClient {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    private final RestTemplate restTemplate;

    public WeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponseDTO fetchWeatherForForecast(double lat, double lon) {
        String url = buildUrlForForecast(lat, lon);
        return restTemplate.getForObject(url, WeatherResponseDTO.class);
    }

    public WeatherResponseDTO fetchWeatherForSummary(double lat, double lon) {
        String url = buildUrlForSummary(lat, lon);
        return restTemplate.getForObject(url, WeatherResponseDTO.class);
    }

    private String buildUrlForForecast(double lat, double lon) {
        String url = UriComponentsBuilder
                .fromHttpUrl(weatherApiUrl)
                .queryParam("latitude", lat)
                .queryParam("longitude", lon)
                .queryParam("daily", "temperature_2m_max,temperature_2m_min,weathercode,sunshine_duration")
                .queryParam("timezone", "auto")
                .toUriString();
        return url;
    }

    private String buildUrlForSummary(double lat, double lon) {
        String url = UriComponentsBuilder
                .fromHttpUrl(weatherApiUrl)
                .queryParam("latitude", lat)
                .queryParam("longitude", lon)
                .queryParam("daily", "sunshine_duration,temperature_2m_min,temperature_2m_max,precipitation_sum")
                .queryParam("hourly", "pressure_msl")
                .queryParam("timezone", "auto")
                .toUriString();
        return url;
    }
}
