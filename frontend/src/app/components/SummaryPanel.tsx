interface SummaryPanelProps {
  minTemperature: number;
  maxTemperature: number;
  averagePressure: number;
  averageSunshineHours: number;
  weatherSummary: string;
}

export default function SummaryPanel({
  minTemperature,
  maxTemperature,
  averagePressure,
  averageSunshineHours,
  weatherSummary,
}: SummaryPanelProps) {
  return (
    <div className="summary-panel">
      <div className="summary-stat">
        <span className="stat-label">Temp. max</span>
        <span className="stat-value">{maxTemperature.toFixed(1)}°C</span>
      </div>
      <div className="summary-stat">
        <span className="stat-label">Temp. min</span>
        <span className="stat-value">{minTemperature.toFixed(1)}°C</span>
      </div>
      <div className="summary-stat">
        <span className="stat-label">Ciśnienie</span>
        <span className="stat-value">{Math.round(averagePressure)}</span>
        <span className="stat-sub">hPa (średnia)</span>
      </div>
      <div className="summary-stat">
        <span className="stat-label">Nasłonecznienie</span>
        <span className="stat-value">{averageSunshineHours.toFixed(1)}</span>
        <span className="stat-sub">godz./dzień</span>
      </div>

      <div className="summary-text">
        {weatherSummary === "no rainfall" || !weatherSummary
          ? "🌤️ No rainfall"
          : `🌧️ ${weatherSummary}`}
      </div>
    </div>
  );
}
