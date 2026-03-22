// Przykład jak powinien wyglądać DayForecast używając nowych klas CSS.
// Dostosuj propsy do swoich typów.

interface DayForecastProps {
    date: string;
    maxTemp: number;
    minTemp: number;
    energy: number;
    icon: string;
  }
  
  export default function DayForecast({ date, maxTemp, minTemp, energy, icon }: DayForecastProps) {
    const label = new Date(date).toLocaleDateString("pl-PL", { weekday: "short", day: "numeric" });
  
    return (
      <div className="day-card">
        <span className="day-label">{label}</span>
        <span className="day-icon">{icon}</span>
        <span className="day-temp-max">{maxTemp}°C</span>
        <span className="day-temp-min">{minTemp}°C</span>
        <span className="day-energy">⚡ {energy} kWh</span>
      </div>
    );
  }