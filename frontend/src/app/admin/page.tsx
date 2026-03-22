"use client";

import { useEffect, useState } from "react";
import { useAuth } from "@/app/context/AuthContext";
import { withAuth } from "@/app/components/withAuth";

interface CityCount {
  city: string;
  count: number;
}

interface StatsResponse {
  totalSearches: number;
  topCities: CityCount[];
}

const SPRING_URL =
  process.env.NEXT_PUBLIC_SPRING_URL ?? "http://localhost:8080";

function AdminPage() {
  const { user, logout } = useAuth();
  const [stats, setStats] = useState<StatsResponse | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch(`${SPRING_URL}/v1/admin/stats?top=10`, {
      credentials: "include",
    })
      .then((res) => {
        if (!res.ok) throw new Error("Brak dostępu");
        return res.json();
      })
      .then(setStats)
      .catch((e) => setError(e.message))
      .finally(() => setIsLoading(false));
  }, []);

  return (
    <div className="main-page">
      <header className="top-bar">
        <span className="logo">⚙️ Admin Panel</span>
        <div className="user-info">
          <span className="role-badge">ADMIN</span>
          <span>{user?.username}</span>
          <button onClick={logout} className="logout-btn">
            Wyloguj
          </button>
        </div>
      </header>

      <main className="weather-page">
        <h1 className="weather-title">Dashboard</h1>

        {isLoading && (
          <div
            className="loading-screen"
            style={{ minHeight: "unset", padding: "4rem 0" }}
          >
            <span className="loading-icon">⚙️</span>
            <p>Ładowanie statystyk…</p>
          </div>
        )}

        {error && (
          <div
            className="auth-error"
            style={{ maxWidth: 500, margin: "2rem auto" }}
          >
            ⚠️ {error}
          </div>
        )}

        {stats && (
          <>
            {/* Total searches */}
            <div className="summary-panel" style={{ marginBottom: "1.5rem" }}>
              <div className="summary-stat">
                <span className="stat-label">Łączne wyszukiwania</span>
                <span className="stat-value">{stats.totalSearches}</span>
              </div>
              <div className="summary-stat">
                <span className="stat-label">Unikalne miasta</span>
                <span className="stat-value">{stats.topCities.length}</span>
                <span className="stat-sub">w top 10</span>
              </div>
            </div>

            {/* Top cities table */}
            <div
              style={{
                background: "var(--surface)",
                border: "1px solid var(--border)",
                borderRadius: "20px",
                overflow: "hidden",
              }}
            >
              <div
                style={{
                  padding: "1.25rem 1.5rem",
                  borderBottom: "1px solid var(--border)",
                  display: "flex",
                  alignItems: "center",
                  gap: "0.5rem",
                }}
              >
                <span style={{ fontSize: "1rem" }}>🏙️</span>
                <span
                  style={{
                    fontFamily: "Sora, sans-serif",
                    fontWeight: 600,
                    fontSize: "0.95rem",
                  }}
                >
                  Najczęściej wyszukiwane miasta
                </span>
              </div>

              <table style={{ width: "100%", borderCollapse: "collapse" }}>
                <thead>
                  <tr style={{ background: "var(--surface-2)" }}>
                    <th style={thStyle}>#</th>
                    <th style={thStyle}>Miasto</th>
                    <th style={{ ...thStyle, textAlign: "right" }}>
                      Wyszukiwania
                    </th>
                    <th style={{ ...thStyle, textAlign: "right" }}>Udział</th>
                  </tr>
                </thead>
                <tbody>
                  {stats.topCities.map((city, i) => {
                    const pct =
                      stats.totalSearches > 0
                        ? ((city.count / stats.totalSearches) * 100).toFixed(1)
                        : "0";
                    return (
                      <tr
                        key={city.city}
                        style={{
                          borderTop: "1px solid var(--border)",
                          transition: "background 0.15s",
                        }}
                        onMouseEnter={(e) =>
                          (e.currentTarget.style.background =
                            "var(--surface-2)")
                        }
                        onMouseLeave={(e) =>
                          (e.currentTarget.style.background = "transparent")
                        }
                      >
                        <td style={tdStyle}>
                          <span
                            style={{
                              color: i < 3 ? "var(--accent)" : "var(--muted)",
                              fontWeight: i < 3 ? 700 : 400,
                              fontFamily: "Sora, sans-serif",
                            }}
                          >
                            {i + 1}
                          </span>
                        </td>
                        <td style={tdStyle}>
                          <span style={{ fontWeight: 500 }}>
                            {city.city ?? "Nieznane"}
                          </span>
                        </td>
                        <td style={{ ...tdStyle, textAlign: "right" }}>
                          <span
                            style={{
                              background: "var(--accent-glow)",
                              color: "var(--accent)",
                              borderRadius: "6px",
                              padding: "0.2rem 0.6rem",
                              fontSize: "0.85rem",
                              fontWeight: 600,
                            }}
                          >
                            {city.count}
                          </span>
                        </td>
                        <td
                          style={{
                            ...tdStyle,
                            textAlign: "right",
                            color: "var(--muted)",
                            fontSize: "0.85rem",
                          }}
                        >
                          {pct}%
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>

              {stats.topCities.length === 0 && (
                <div
                  style={{
                    padding: "3rem",
                    textAlign: "center",
                    color: "var(--muted)",
                  }}
                >
                  Brak danych — nikt jeszcze nie wyszukiwał miast
                </div>
              )}
            </div>
          </>
        )}
      </main>
    </div>
  );
}

const thStyle: React.CSSProperties = {
  padding: "0.75rem 1.5rem",
  textAlign: "left",
  fontSize: "0.72rem",
  fontWeight: 600,
  color: "var(--muted)",
  textTransform: "uppercase",
  letterSpacing: "0.06em",
};

const tdStyle: React.CSSProperties = {
  padding: "0.9rem 1.5rem",
  fontSize: "0.9rem",
  color: "var(--text)",
};

export default withAuth(AdminPage, { allowedRoles: ["ADMIN"] });
