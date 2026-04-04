package weather.forecast.app.historical;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import weather.forecast.app.user.User;

@Service
@RequiredArgsConstructor
class SearchLogService implements SearchLogFacade {

    private final SearchLogRepository searchLogRepository;

    @Override
    // Zapisuje historię wyszukiwań użytkowników – używane do statystyk w panelu admina
    public void log(User user, String city, Double lat, Double lon) {
        searchLogRepository.save(SearchLog.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .city(city)
                .lat(lat)
                .lon(lon)
                .build());
    }
}
