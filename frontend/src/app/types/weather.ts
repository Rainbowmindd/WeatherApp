export interface DayForecastData {
    date: string;
    maxTemperature: number;
    minTemperature: number;
    estimatedEnergyKWh: number;
    weatherCode: number;
}

export interface WeeklySummaryData {
    minTemperature: number;
    maxTemperature: number;
    averagePressure: number;
    averageSunshineHours: number;
    weatherSummary: string;
}