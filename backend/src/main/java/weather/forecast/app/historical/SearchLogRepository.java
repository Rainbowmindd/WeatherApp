package weather.forecast.app.historical;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SearchLogRepository extends MongoRepository<SearchLog, String> {
    List<SearchLog> findByUserIdOrderBySearchedAtDesc(String userId);
}
