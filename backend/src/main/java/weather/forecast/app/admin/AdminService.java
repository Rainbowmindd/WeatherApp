package weather.forecast.app.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import weather.forecast.app.admin.dto.CityCount;
import weather.forecast.app.admin.dto.StatsResponse;
import weather.forecast.app.historical.SearchLogRepository;

@Service
@RequiredArgsConstructor
class AdminService {

    private final SearchLogRepository searchLogRepository;
    private final MongoTemplate mongoTemplate;

    public StatsResponse getStats(int topN) {

        // Budujemy potok agregacji w MongoDB:
        // 1. Grupujemy logi po polu "city" i liczymy wystąpienia każdego miasta
        // 2. Wyciągamy z wyniku pole "count" oraz mapujemy "_id" na "city"
        // 3. Sortujemy malejąco – najpopularniejsze miasta na górze
        // 4. Ograniczamy wynik do topN rekordów
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("city").count().as("count"),
                Aggregation.project("count").and("_id").as("city"),
                Aggregation.sort(Sort.Direction.DESC, "count"),
                Aggregation.limit(topN)
        );

        // Wykonujemy agregację na kolekcji "search_logs" i mapujemy wyniki na obiekty CityCount
        AggregationResults<CityCount> results = mongoTemplate.aggregate(
                aggregation, "search_logs", CityCount.class
        );

        // Zwracamy całkowitą liczbę logów oraz listę najpopularniejszych miast
        return new StatsResponse(searchLogRepository.count(), results.getMappedResults());
    }
}
