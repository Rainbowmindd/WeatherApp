package weather.forecast.app.historical;

import weather.forecast.app.user.User;

public interface SearchLogFacade {
    void log(User user, String city, Double lat, Double lon);
}
