// src/routes.js
import React, { Suspense, lazy } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import ProtectedRoute from "./components/common/ProtectedRoute";
import LoadingSpinner from "./components/common/LoadingSpinner";

// lazy-loaded pages / components for faster initial load (optional)
const HomePage = lazy(() => import("./pages/HomePage"));
const AboutUs = lazy(() => import("./pages/AboutUs"));
const ContactUs = lazy(() => import("./pages/ContactUs"));
const Faq = lazy(() => import("./pages/Faq"));
const NotFound = lazy(() => import("./pages/NotFound"));

/* Admin area */
const AdminLayout = lazy(() => import("./components/admin/AdminLayout"));
const UsersPage = lazy(() => import("./components/admin/UsersPage"));
const ReportsPage = lazy(() => import("./components/admin/ReportsPage"));
const PermissionsPage = lazy(() => import("./components/admin/PermissionsPage"));
const DashboardPage = lazy(() => import("./components/admin/DashboardPage"));

/* Ops area */
const OpsDashboardPage = lazy(() => import("./components/ops/OpsDashboardPage"));

/* Auth */
const LoginForm = lazy(() => import("./components/auth/LoginForm"));
const ForgotPasswordModal = lazy(() => import("./components/auth/ForgotPasswordModal"));

/**
 * Centralized route definitions. Use <RoutesConfig /> inside your App or index.
 * ProtectedRoute should check auth and roles and either render children or redirect to /login.
 */
export default function RoutesConfig() {
  return (
    <Suspense fallback={<LoadingSpinner />}>
      <Routes>
        {/* Public routes */}
        <Route path="/" element={<HomePage />} />
        <Route path="/about" element={<AboutUs />} />
        <Route path="/contact" element={<ContactUs />} />
        <Route path="/faq" element={<Faq />} />

        {/* Auth */}
        <Route path="/login" element={<LoginForm />} />
        <Route path="/forgot-password" element={<ForgotPasswordModal />} />

        {/* Admin area - protected (example: role ADMIN) */}
        <Route
          path="/admin/*"
          element={
            <ProtectedRoute requiredRole="ADMIN">
              <AdminLayout />
            </ProtectedRoute>
          }
        >
          {/* nested admin routes rendered inside AdminLayout (Outlet) */}
          <Route index element={<Navigate to="users" replace />} />
          <Route path="users" element={<UsersPage />} />
          <Route path="reports" element={<ReportsPage />} />
          <Route path="permissions" element={<PermissionsPage />} />
          <Route path="dashboard" element={<DashboardPage />} />
        </Route>

        {/* Ops user area - protected (example: role OPS) */}
        <Route
          path="/ops"
          element={
            <ProtectedRoute requiredRole="OPS">
              <OpsDashboardPage />
            </ProtectedRoute>
          }
        />

        {/* Legacy or alternate dashboards */}
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <DashboardPage />
            </ProtectedRoute>
          }
        />

        {/* Catch-all */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Suspense>
  );
}
