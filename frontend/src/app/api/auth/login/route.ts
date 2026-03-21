import { NextRequest, NextResponse } from "next/server";

// ---------- MOCK DATA (zastąp prawdziwym wywołaniem Spring Boot) ----------
const MOCK_USERS = [
  { id: "1", email: "user@test.pl",  password: "user123",  name: "Jan Kowalski", role: "USER"  },
  { id: "2", email: "admin@test.pl", password: "admin123", name: "Admin Root",   role: "ADMIN" },
];
// -------------------------------------------------------------------------

export async function POST(req: NextRequest) {
  const { email, password } = await req.json();

  // --- Docelowo: wywołanie Spring Boot ---
  // const res = await fetch("http://localhost:8080/api/auth/login", {
  //   method: "POST",
  //   headers: { "Content-Type": "application/json" },
  //   body: JSON.stringify({ email, password }),
  // });
  // if (!res.ok) return NextResponse.json({ message: "Zły email lub hasło" }, { status: 401 });
  // const data = await res.json();
  // return NextResponse.json(data);

  const found = MOCK_USERS.find(
    (u) => u.email === email && u.password === password
  );

  if (!found) {
    return NextResponse.json(
      { message: "Zły email lub hasło" },
      { status: 401 }
    );
  }

  const { password: _pw, ...safeUser } = found;
  return NextResponse.json(safeUser);
}
