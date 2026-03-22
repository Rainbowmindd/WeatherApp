import { NextRequest, NextResponse } from "next/server";

const SPRING_URL = process.env.SPRING_API_URL ?? "http://localhost:8080";

export async function POST(req: NextRequest) {
  const cookie = req.headers.get("cookie") ?? "";

  await fetch(`${SPRING_URL}/auth/logout`, {
    method: "POST",
    headers: { Cookie: cookie },
  });

  const response = NextResponse.json({ ok: true });
  // Wyczyść JSESSIONID po stronie przeglądarki
  response.headers.set(
    "set-cookie",
    "JSESSIONID=; Path=/; Max-Age=0; HttpOnly; SameSite=Lax"
  );
  return response;
}