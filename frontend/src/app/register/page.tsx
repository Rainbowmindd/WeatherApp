"use client";

import { useState, FormEvent } from "react";
import Link from "next/link";
import { useAuth } from "@/app/context/AuthContext";

export default function RegisterPage() {
  const { register, isLoading } = useAuth();
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirm, setConfirm] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError("");

    if (password !== confirm) {
      setError("Hasła nie są identyczne");
      return;
    }
    if (password.length < 6) {
      setError("Hasło musi mieć minimum 6 znaków");
      return;
    }

    try {
      await register(username, email, password);
    } catch (err: unknown) {
      setError(err instanceof Error ? err.message : "Wystąpił błąd");
    }
  };

  return (
    <main className="auth-page">
      <div className="auth-card">
        <div className="auth-header">
          <div className="auth-icon">🌤️</div>
          <h1>Nowe konto</h1>
          <p>Dołącz i sprawdzaj pogodę</p>
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
            <label htmlFor="email">Email</label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="jan@example.com"
              required
              autoComplete="email"
            />
          </div>

          <div className="field">
            <label htmlFor="password">Hasło</label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Min. 6 znaków"
              required
              autoComplete="new-password"
            />
          </div>

          <div className="field">
            <label htmlFor="confirm">Potwierdź hasło</label>
            <input
              id="confirm"
              type="password"
              value={confirm}
              onChange={(e) => setConfirm(e.target.value)}
              placeholder="••••••••"
              required
              autoComplete="new-password"
            />
          </div>

          <button type="submit" className="auth-btn" disabled={isLoading}>
            {isLoading ? "Rejestracja..." : "Zarejestruj się"}
          </button>
        </form>

        <p className="auth-switch">
          Masz już konto?{" "}
          <Link href="/login">Zaloguj się</Link>
        </p>
      </div>
    </main>
  );
}