import { NextRequest, NextResponse } from "next/server";

const SPRING_URL = process.env.SPRING_API_URL ?? "http://localhost:8080";

export async function POST(req: NextRequest) {
  const body = await req.json();

  const springRes = await fetch(`${SPRING_URL}/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });

  const data = await springRes.json();

  if (!springRes.ok) {
    return NextResponse.json(
      { message: data.message ?? "Błąd rejestracji" },
      { status: springRes.status }
    );
  }

  // Przepisz JSESSIONID do przeglądarki
  const response = NextResponse.json(data, { status: 201 });
  const setCookie = springRes.headers.get("set-cookie");
  if (setCookie) response.headers.set("set-cookie", setCookie);

  return response;
}
