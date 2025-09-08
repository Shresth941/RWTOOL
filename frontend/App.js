// src/App.js
import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

/* Layouts & common */
import Header from "./components/layout/Header/Header";
import Footer from "./components/layout/Footer/Footer";
import AdminLayout from "./components/admin/AdminLayout";

/* Pages */
import HomePage from "./pages/HomePage";
import AboutUs from "./pages/AboutUs";
import ContactUs from "./pages/ContactUs";
import Faq from "./pages/Faq";
import NotFound from "./pages/NotFound";

/* Auth */
import LoginForm from "./components/auth/LoginForm";
import ForgotPasswordModal from "./components/auth/ForgotPasswordModal";

/* Admin pages */
import UsersPage from "./components/admin/UsersPage";
import ReportsPage from "./components/admin/ReportsPage";
import PermissionsPage from "./components/admin/PermissionsPage";
import DashboardPage from "./components/admin/DashboardPage";

/* Ops */
import OpsDashboardPage from "./components/ops/OpsDashboardPage";

/* Optional: ProtectedRoute if you add auth context */
import ProtectedRoute from "./components/common/ProtectedRoute";

function App() {
  return (
    <BrowserRouter>
      <div className="app-root">
        {/* Header sits outside routes so it shows on all pages.
            If you want different headers per area, move into layouts. */}
        <Header />

        <main className="app-main">
          <Routes>
            {/* Public pages */}
            <Route path="/" element={<HomePage />} />
            <Route path="/about" element={<AboutUs />} />
            <Route path="/contact" element={<ContactUs />} />
            <Route path="/faq" element={<Faq />} />

            {/* Auth */}
            <Route path="/login" element={<LoginForm />} />
            <Route path="/forgot-password" element={<ForgotPasswordModal />} />

            {/* Ops user */}
            <Route
              path="/userlogin"
              element={
                <ProtectedRoute>
                  <OpsDashboardPage />
                </ProtectedRoute>
              }
            />

            {/* Admin section (nested) */}
            <Route
              path="/admin/*"
              element={
                <ProtectedRoute requireRole="ADMIN">
                  <AdminLayout />
                </ProtectedRoute>
              }
            >
              {/* nested admin routes inside AdminLayout via outlet */}
              <Route index element={<Navigate to="users" replace />} />
              <Route path="users" element={<UsersPage />} />
              <Route path="reports" element={<ReportsPage />} />
              <Route path="permissions" element={<PermissionsPage />} />
              <Route path="dashboard" element={<DashboardPage />} />
            </Route>

            {/* fallback */}
            <Route path="*" element={<NotFound />} />
          </Routes>
        </main>

        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;
