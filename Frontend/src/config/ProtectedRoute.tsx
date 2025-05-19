import { Navigate } from "react-router-dom";
import { isAuthenticated, getUserData } from "./Auth";
import { JSX } from "react";

interface ProtectedRouteProps {
  children: JSX.Element;
  allowedRoles?: string[];
}

const ProtectedRoute = ({ children, allowedRoles }: ProtectedRouteProps) => {
  if (!isAuthenticated()) {
    return <Navigate to="/login" />;
  }

  const user = getUserData();

  if (allowedRoles && (!user || !allowedRoles.includes(user.type))) {
    return <Navigate to="/unauthorized" />;
  }

  return children;
};

export default ProtectedRoute;
