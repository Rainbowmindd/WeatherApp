package weather.forecast.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import weather.forecast.app.DTO.*;
import weather.forecast.app.services.EnergyCalculator;
import weather.forecast.app.services.ForecastBuilder;
import weather.forecast.app.services.WeatherCalculator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUnitTests {

	private final EnergyCalculator energyCalculator = new EnergyCalculator();
	private final RestTemplate restTemplate = new RestTemplate();
	private final WeatherCalculator weatherCalculator = new WeatherCalculator();
	private final MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
	private final ForecastBuilder builder = new ForecastBuilder(energyCalculator);

	@Test
	void testCalculateEnergyKWh() {
		double seconds = 7200;
		double expected = 2.5 * 2 * 0.2;

		double result = energyCalculator.calculateEnergyKWh(seconds);

		assertEquals(expected, result, 0.0001);
	}

	@Test
	void testZeroSunshine() {
		assertEquals(0, energyCalculator.calculateEnergyKWh(0));
	}

	@Test
	void testCalculateWeeklySummary() {
		DailyWeatherDTO daily = new DailyWeatherDTO();
		daily.setTemperature_2m_max(List.of(20.0, 21.0, 19.0, 22.0, 18.0, 20.0, 21.0));
		daily.setTemperature_2m_min(List.of(10.0, 11.0, 9.0, 12.0, 8.0, 10.0, 11.0));
		daily.setSunshine_duration(List.of(3600.0, 7200.0, 1800.0, 5400.0, 6000.0, 3600.0, 7200.0));
		daily.setPrecipitation_sum(List.of(5.0, 0.0, 0.0, 1.0, 0.0, 0.0, 2.0));
		daily.setWeathercode(List.of(61, 0, 0, 61, 0, 0, 0));

		HourlyWeatherDTO hourly = new HourlyWeatherDTO();
		List<Double> hourlyPressure = new ArrayList<>();
		for (int i = 0; i < 168; i++) {
			hourlyPressure.add(1015.0 + (i % 3));  // dummy variation
		}
		hourly.setPressure_msl(hourlyPressure);

		WeatherResponseDTO response = new WeatherResponseDTO();
		response.setDaily(daily);
		response.setHourly(hourly);

		WeeklySummaryDTO summary = weatherCalculator.calculateSummary(response);

		System.out.println(summary.getAveragePressure() + " " + summary.getAverageSunshineHours() + " "+ summary.getMaxTemperature() + " " + summary.getMinTemperature());

		assertEquals(summary.getAveragePressure(), 1016, 0.001);
		assertEquals(summary.getAverageSunshineHours(), 1.380, 0.001);
		assertEquals(summary.getMaxTemperature(), 22, 0.001);
		assertEquals(summary.getMinTemperature(), 8, 0.001);
		assertNotNull(summary.getWeatherSummary());
	}

	@Test
	void testBuildForecast() {
		DailyWeatherDTO daily = new DailyWeatherDTO();
		daily.setTime(List.of("2025-06-28","2025-06-29","2025-06-30","2025-07-01","2025-07-02","2025-07-03","2025-07-04"));
		daily.setWeathercode(List.of(1,1,1,1,1,1,1));
		daily.setTemperature_2m_max(List.of(25.0,26.0,27.0,28.0,29.0,30.0,31.0));
		daily.setTemperature_2m_min(List.of(15.0,16.0,17.0,18.0,19.0,20.0,21.0));
		daily.setSunshine_duration(List.of(3600.0,3600.0,3600.0,3600.0,3600.0,3600.0,3600.0));

		WeatherResponseDTO response = new WeatherResponseDTO();
		response.setDaily(daily);

		List<DailyForecastDTO> forecast = builder.build(response);

		assertEquals(7, forecast.size());
		DailyForecastDTO day = forecast.get(0);
		assertEquals("2025-06-28", day.getDate());
		assertEquals(day.getEstimatedEnergyKWh(), 0.5, 0.001);
	}

}
