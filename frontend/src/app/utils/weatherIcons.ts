export function mapWeatherCodeToIcon(code: number): string {
    if (code === 0) return "☀️";
    if (code >= 1 && code <= 3) return "⛅";
    if (code >= 50 && code <= 99) return "🌧️";
    return "❓";
}