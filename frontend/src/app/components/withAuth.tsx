"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { useAuth, UserRole } from "@/app/context/AuthContext";

interface WithAuthProps {
  allowedRoles?: UserRole[];
}

export function withAuth<T extends object>(
  WrappedComponent: React.ComponentType<T>,
  { allowedRoles }: WithAuthProps = {}
) {
  return function ProtectedPage(props: T) {
    const { user, isLoading } = useAuth();
    const router = useRouter();

    useEffect(() => {
      if (isLoading) return;

      if (!user) {
        router.replace("/login");
        return;
      }

      if (allowedRoles && !allowedRoles.includes(user.role)) {
        // Wrong role — redirect to the right page
        router.replace(user.role === "ADMIN" ? "/admin" : "/weather");
      }
    }, [user, isLoading, router]);

    if (isLoading || !user) {
      return (
        <div className="loading-screen">
          <span className="loading-icon">⛅</span>
          <p>Ładowanie...</p>
        </div>
      );
    }

    if (allowedRoles && !allowedRoles.includes(user.role)) {
      return null; // redirect in progress
    }

    return <WrappedComponent {...props} />;
  };
}
