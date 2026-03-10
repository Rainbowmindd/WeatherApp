"use client";

interface SummaryPanelProps {
    maxTemperature: number;
    minTemperature: number;
    averagePressure: number;
    averageSunshineHours: number;
    weatherSummary: string;
}

export default function SummaryPanel({maxTemperature, minTemperature, averagePressure, averageSunshineHours, weatherSummary}: SummaryPanelProps) {

    return (
        <footer className="text-center border-t mb-10 pt-4 w-full">
            <p className={"text-2xl mb-4"}> This week, in a nutshell: </p>
            <p>
                Max: {maxTemperature}°C &nbsp;
                Min: {minTemperature}°C &nbsp;
            </p>
            <p>
                Averages: &nbsp;
                {averagePressure.toFixed(2)} hPa &nbsp;
                ☀️ {averageSunshineHours.toFixed(1)}h
            </p>
            <p className="mt-2">{weatherSummary}</p>
        </footer>
    )
}