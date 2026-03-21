import type { Metadata } from "next";
import { AuthProvider } from "@/app/context/AuthContext";
import "./globals.css";

export const metadata: Metadata = {
  title: "WeatherApp",
  description: "Twoja aplikacja pogodowa",
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="pl">
      <body>
        <AuthProvider>{children}</AuthProvider>
      </body>
    </html>
  );
}