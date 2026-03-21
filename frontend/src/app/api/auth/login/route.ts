import { NextRequest, NextResponse } from "next/server";

const SPRING_URL = process.env.SPRING_API_URL ?? "http://localhost:8080";

export async function POST(req: NextRequest) {
  const body = await req.json();

  const springRes = await fetch(`${SPRING_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });

  const text = await springRes.text();

  let data: unknown;
  try {
    data = JSON.parse(text);
  } catch {
    if (!springRes.ok) {
      return NextResponse.json({ message: text }, { status: 401 });
    }
    return NextResponse.json({ message: "Zalogowano" }, { status: 200 });
  }

  if (!springRes.ok) {
    const msg =
      (data as Record<string, string>)?.message ?? "Zły login lub hasło";
    return NextResponse.json({ message: msg }, { status: 401 });
  }

  const response = NextResponse.json(data);
  const setCookie = springRes.headers.get("set-cookie");
  if (setCookie) response.headers.set("set-cookie", setCookie);

  return response;
}
