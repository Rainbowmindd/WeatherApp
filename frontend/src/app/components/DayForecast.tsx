"use client";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { IconDefinition } from "@fortawesome/fontawesome-svg-core";

interface DayForecastProps {
    date: string;
    maxTemp: number;
    minTemp: number;
    energy: number;
    icon: IconDefinition;
}

const formatDate = (isoString: string) => {
    const date = new Date(isoString);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are zero-based
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
};

export default function DayForecast({date, maxTemp, minTemp, energy, icon}: DayForecastProps) {
    return (
        <div className="flex flex-col items-center rounded-lg bg-[var(--panels)] p-4 shadow text-center">
            <p className="mb-2">{formatDate(date)}</p>
            <FontAwesomeIcon icon={icon} size="3x" className="mb-2" />
            <p className="text-xl">{maxTemp}°C</p>
            <p className="text-lg">{minTemp}°C</p>
            <p className="mt-2">☀️ {energy}kWh</p>
        </div>
    );
}
