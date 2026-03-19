package weather.forecast.app.historical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "search_logs")
public class SearchLog {

    @Id
    private String id;

    private String userId;
    private String username;
    private String city;
    private Double lat;
    private Double lon;

    @Builder.Default
    private Instant searchedAt = Instant.now();
}