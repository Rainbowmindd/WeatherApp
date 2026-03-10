package weather.forecast.app.services;

import org.springframework.stereotype.Component;

@Component
public class EnergyCalculator {

    private static final double PANEL_POWER_KW = 2.5;
    private static final double PANEL_EFFICIENCY = 0.2;

    public double calculateEnergyKWh(double sunshineSeconds) {
        double hours = sunshineSeconds / 3600.0;
        return PANEL_POWER_KW * hours * PANEL_EFFICIENCY;
    }
}
