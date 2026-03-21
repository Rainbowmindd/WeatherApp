"use client";

import { useState, FormEvent } from "react";
import Link from "next/link";
import { useAuth } from "@/app/context/AuthContext";

export default function LoginPage() {
  const { login, isLoading } = useAuth();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError("");
    try {
      await login(username, password);
    } catch (err: unknown) {
      setError(err instanceof Error ? err.message : "Wystąpił błąd");
    }
  };

  return (
    <main className="auth-page">
      <div className="auth-card">
        <div className="auth-header">
          <div className="auth-icon">⛅</div>
          <h1>Witaj z powrotem</h1>
          <p>Zaloguj się, żeby zobaczyć pogodę</p>
        </div>

        <form onSubmit={handleSubmit} className="auth-form">
          {error && <div className="auth-error">{error}</div>}

          <div className="field">
            <label htmlFor="username">Nazwa użytkownika</label>
            <input
              id="username"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="jan_kowalski"
              required
              autoComplete="username"
            />
          </div>

          <div className="field">
            <label htmlFor="password">Hasło</label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
              required
              autoComplete="current-password"
            />
          </div>

          <button type="submit" className="auth-btn" disabled={isLoading}>
            {isLoading ? "Logowanie..." : "Zaloguj się"}
          </button>
        </form>

        <p className="auth-switch">
          Nie masz konta?{" "}
          <Link href="/register">Zarejestruj się</Link>
        </p>
      </div>
    </main>
  );
}