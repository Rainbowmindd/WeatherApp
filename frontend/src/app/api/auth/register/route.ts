import { NextRequest, NextResponse } from "next/server";

export async function POST(req: NextRequest) {
  const { name, email, password } = await req.json();

  // --- Docelowo: wywołanie Spring Boot ---
  // const res = await fetch("http://localhost:8080/api/auth/register", {
  //   method: "POST",
  //   headers: { "Content-Type": "application/json" },
  //   body: JSON.stringify({ name, email, password }),
  // });
  // if (!res.ok) {
  //   const err = await res.json();
  //   return NextResponse.json({ message: err.message || "Błąd rejestracji" }, { status: res.status });
  // }
  // const data = await res.json();
  // return NextResponse.json(data);

  // MOCK: nowi użytkownicy zawsze dostają rolę USER
  const newUser = {
    id: crypto.randomUUID(),
    email,
    name,
    role: "USER" as const,
  };

  return NextResponse.json(newUser, { status: 201 });
}
