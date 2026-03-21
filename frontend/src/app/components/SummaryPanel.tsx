// Przykład SummaryPanel używając nowych klas CSS.

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
        <span className="stat-value">{maxTemperature}°C</span>
      </div>
      <div className="summary-stat">
        <span className="stat-label">Temp. min</span>
        <span className="stat-value">{minTemperature}°C</span>
      </div>
      <div className="summary-stat">
        <span className="stat-label">Ciśnienie</span>
        <span className="stat-value">{averagePressure}</span>
        <span className="stat-sub">hPa (średnia)</span>
      </div>
      <div className="summary-stat">
        <span className="stat-label">Nasłonecznienie</span>
        <span className="stat-value">{averageSunshineHours.toFixed(1)}</span>
        <span className="stat-sub">godz./dzień</span>
      </div>
      <p className="summary-text">{weatherSummary}</p>
    </div>
  );
}
