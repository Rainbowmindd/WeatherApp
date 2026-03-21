"use client";

import { useEffect, useState } from "react";
import { mapWeatherCodeToIcon } from "@/app/utils/weatherIcons";
import DayForecast from "@/app/components/DayForecast";
import DarkModeButton from "@/app/components/DarkModeButton";
import SummaryPanel from "@/app/components/SummaryPanel";
import { useAuth } from "@/app/context/AuthContext";
import { withAuth } from "@/app/components/withAuth";
import dynamic from "next/dynamic";
import { DayForecastData, WeeklySummaryData } from "@/app/types/weather";

const MapPicker = dynamic(() => import("@/app/components/MapPicker"), {
  ssr: false,
});

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL;

function WeatherHomePage() {
  const { user, logout } = useAuth();

  const [lat, setLat] = useState<number | null>(null);
  const [lon, setLon] = useState<number | null>(null);
  const [showMap, setShowMap] = useState(false);
  const [forecast, setForecast] = useState<DayForecastData[]>([]);
  const [summary, setSummary] = useState<WeeklySummaryData | null>(null);

  const handleLocation = (lat: number, lon: number) => {
    setLat(lat);
    setLon(lon);
  };

  useEffect(() => {
    navigator.geolocation.getCurrentPosition((position) => {
      setLat(position.coords.latitude);
      setLon(position.coords.longitude);
    });
  }, []);

  useEffect(() => {
    console.log("lat/lon:", lat, lon);
    if (!API_BASE_URL || lat === null || lon === null) return;

    console.log("fetching:", `${API_BASE_URL}/today?lat=${lat}&lon=${lon}`);

    fetch(`${API_BASE_URL}/today?lat=${lat}&lon=${lon}`)
      .then((res) => res.json())
      .then((data) => {
        console.log("forecast data:", data);
        setForecast(data);
      })
      .catch(console.error);

    fetch(`${API_BASE_URL}/weekly-summary?lat=${lat}&lon=${lon}`)
      .then((res) => res.json())
      .then((data) => {
        console.log("summary data:", data);
        setSummary(data);
      })
      .catch(console.error);
  }, [lat, lon]);

  return (
    <div className="main-page">
      {/* ── Top bar ── */}
      <header className="top-bar">
        <span className="logo">⛅ Whatstheweather?</span>
        <div className="user-info">
          <span>{user?.username}</span>
          <button onClick={logout} className="logout-btn">
            Wyloguj
          </button>
        </div>
      </header>

      {/* ── Content ── */}
      <main className="weather-page">
        <h1 className="weather-title">Whatstheweather?</h1>

        {/* 7-day forecast */}
        <div className="forecast-grid">
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

        {/* Weekly summary */}
        {summary ? (
          <SummaryPanel
            minTemperature={summary.minTemperature}
            maxTemperature={summary.maxTemperature}
            averagePressure={summary.averagePressure}
            averageSunshineHours={summary.averageSunshineHours}
            weatherSummary={summary.weatherSummary}
          />
        ) : (
          <p className="api-loading">
            ⏳ Ładowanie API, proszę czekać (może potrwać ~2 minuty)…
          </p>
        )}

        {/* Controls */}
        <div className="controls-row">
          <DarkModeButton />
          <button
            onClick={() => setShowMap((v) => !v)}
            className={`ghost-btn ${showMap ? "active" : ""}`}
          >
            🗺️ {showMap ? "Ukryj mapę" : "Wybierz lokalizację"}
          </button>
        </div>

        {/* Map */}
        {showMap && (
          <div className="map-section">
            <h2>Kliknij dowolne miejsce na mapie</h2>
            <MapPicker onLocationSelected={handleLocation} />
          </div>
        )}
      </main>
    </div>
  );
}

export default withAuth(WeatherHomePage, { allowedRoles: ["USER"] });
