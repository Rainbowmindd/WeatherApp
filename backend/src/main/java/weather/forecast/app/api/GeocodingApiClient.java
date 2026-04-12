package weather.forecast.app.api;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
class GeocodingApiClient implements GeographyApiFacade {

    @Value("${geography.api.url}")
    private String geoApiUrl;
    private final RestTemplate restTemplate;

    @Override
    public String getCityName(Double lat, Double lon) {

        // Budujemy URL do API geokodowania z podanymi współrzędnymi
        String url = UriComponentsBuilder
                .fromHttpUrl(geoApiUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("format", "json")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "weather-forecast-app/1.0"); // wymagane przez Nominatim
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        JsonNode response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class).getBody();
        JsonNode address = response.get("address");

        // Nominatim zwraca różne pola w zależności od miejsca – sprawdzamy po kolei
        for (String field : List.of("city", "town", "village", "county")) {
            if (address.has(field)) return address.get(field).asText();
        }
        return "Unknown";
    }
}