export interface DailyForecast {
    date: string;
    weatherCode: number;
    minTemperature: number;
    maxTemperature: number;
    estimatedEnergyKWh: number;
}

export interface WeeklySummary {
    averagePressure: number;
    averageSunshineHours: number;
    minTemperature: number;
    maxTemperature: number;
    weatherSummary: string;
}