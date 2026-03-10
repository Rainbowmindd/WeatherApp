#  Weather Forecast Backend (Spring Boot)

This is the backend for the Weather Forecast application.  
It integrates with the [Open-Meteo API](https://open-meteo.com/en/docs) to provide daily and weekly weather forecasts, and estimates photovoltaic energy generation.

This API is available at https://whatstheweather-backend.onrender.com/v1/weather/

The full application is available at https://whatstheweather-yg8k.onrender.com/ (May take 2 minutes to load the backend and frontend, each)

---

## Endpoints

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| GET | `/v1/weather/today?lat=...&lon=...` | Get 7-day daily forecast |
| GET | `/v1/weather/weekly-summary?lat=...&lon=...` | Get weekly weather summary |

# Features

- Data validation & error handling  
- Integration with Open-Meteo API  
- CORS configured for frontend connection

---

## Tech Stack

- Java 24
- Spring Boot 3.5.3
- Maven
- REST Template / HttpClient
- Deployed on [Render](https://render.com/)


