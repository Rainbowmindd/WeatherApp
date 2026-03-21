"use client";
import { useAuth } from "@/app/context/AuthContext";
import { withAuth } from "@/app/components/withAuth";

function AdminPage() {
  const { user, logout } = useAuth();
  return (
    <div className="main-page">
      <header className="top-bar">
        <span className="logo">⚙️ Admin Panel</span>
        <div className="user-info">
          <span className="role-badge">ADMIN</span>
          <span>{user?.username}</span>
          <button onClick={logout} className="logout-btn">Wyloguj</button>
        </div>
      </header>
      <div className="placeholder-content">
        <div className="big-icon">🛠️</div>
        <h2>Admin Dashboard</h2>
      </div>
    </div>
  );
}

export default withAuth(AdminPage, { allowedRoles: ["ADMIN"] });