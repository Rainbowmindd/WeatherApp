"use client";

import { useEffect, useState } from "react";
import {mapWeatherCodeToIcon} from "@/app/utils/weatherIcons";
import DayForecast from "./components/DayForecast";
import DarkModeButton from "@/app/components/DarkModeButton";
import SummaryPanel from "@/app/components/SummaryPanel";
import dynamic from "next/dynamic";
import {DayForecastData, WeeklySummaryData} from "@/app/types/weather";
//dynamic import because leaflet caused issues
const MapPicker = dynamic(() => import("@/app/components/MapPicker"), {
    ssr: false,
});

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL;

export default function Home() {
    const [lat, setLat] = useState<number | null>(null);
    const [lon, setLon] = useState<number | null>(null);
    const [showMap, setShowMap] = useState(false);

    const [forecast, setForecast] = useState<DayForecastData[]>([]);
    const [summary, setSummary] = useState<WeeklySummaryData | null>(null);

    const handleLocation = (lat: number, lon: number) => {
        setLat(lat);
        setLon(lon);
    };

    const toggleMap = () => {
        setShowMap(!showMap);
    }

    //This gets coords on page load
    useEffect(() => {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                setLat(position.coords.latitude);
                setLon(position.coords.longitude);
            },
        );
    }, []);

    //This fetches data whenever coords update
    useEffect(() => {
        if (!API_BASE_URL) {
            return;
        }
        if (lat !== null && lon !== null) {
            fetch(`${API_BASE_URL}/today?lat=${lat}&lon=${lon}`)
                .then((res) => res.json())
                .then((data) => {
                    setForecast(data);
                })
                .catch((err) => console.error(err));

            fetch(`${API_BASE_URL}/weekly-summary?lat=${lat}&lon=${lon}`)
                .then((res) => res.json())
                .then((data) => {
                    setSummary(data);
                })
                .catch((err) => console.error(err));
        }
    }, [lat, lon]);

    return (
        <main className="flex flex-col items-center min-h-screen p-8">
            <h1 className="text-4xl mt-6 mb-8">Whatstheweather?</h1>


            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-7 gap-4 mb-24">
                {forecast.map((day) => (
                    <DayForecast
                        key={day.date}
                        date={day.date}
                        maxTemp={day.maxTemperature}
                        minTemp={day.minTemperature}
                        energy={Number(day.estimatedEnergyKWh.toFixed(2))}
                        icon={mapWeatherCodeToIcon(day.weatherCode)}
                    />
                ))}
            </div>

            {summary ?
                <SummaryPanel
                    minTemperature={summary.minTemperature}
                    maxTemperature={summary.maxTemperature}
                    averagePressure={summary.averagePressure}
                    averageSunshineHours={summary.averageSunshineHours}
                    weatherSummary={summary.weatherSummary}
                /> : "Loading the API, please be patient (This takes about 2 minutes!)"}
            <div className="grid grid-cols-2 gap-10 mb-4">
                <DarkModeButton/>
                <button onClick={toggleMap} className="mb-4 px-4 py-2 bg-[var(--panels)] rounded">Toggle map</button>
            </div>

            { showMap &&
                <main className="flex flex-col items-center">
                    <h1 className="text-xl mb-4">Pick any point on the map:</h1>
                    <MapPicker onLocationSelected={handleLocation}/>
                </main>
            }
        </main>
    );
}
