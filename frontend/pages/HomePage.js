// src/pages/HomePage.js
import React from "react";
import { Link } from "react-router-dom";
import Header from "../components/layout/Header/Header";
import Footer from "../components/layout/Footer/Footer";
import "../components/HomePage/Homepage.css"; // optional - if you have homepage-specific CSS

export default function HomePage() {
  return (
    <>
      <Header />
      <main className="container py-5">
        <div className="row align-items-center">
          <div className="col-md-6">
            <h1 className="display-5 fw-bold">RW Tool — Reports at Your Fingertips</h1>
            <p className="lead text-muted">
              Secure, automated report processing and management for Wealth Operations.
              Transfer, publish and control access to reports with role-based security.
            </p>
            <p>
              <Link to="/login" className="btn btn-primary me-2">User Login</Link>
              <Link to="/adminform" className="btn btn-outline-primary">Admin Login</Link>
            </p>
          </div>
          <div className="col-md-6">
            {/* placeholder hero image */}
            <div className="home-hero rounded-3" style={{
              backgroundImage: `url('/assets/images/home-hero.jpg')`,
              backgroundSize: 'cover',
              height: 320
            }} />
          </div>
        </div>

        <hr className="my-5" />

        <div className="row text-center">
          <div className="col-md-4 mb-4">
            <h5 className="text-primary">Secure File Transfer</h5>
            <p className="text-muted">Incoming files are transferred automatically and stored securely.</p>
          </div>
          <div className="col-md-4 mb-4">
            <h5 className="text-primary">Smart Processing</h5>
            <p className="text-muted">Automatically transform and route files to the correct folder.</p>
          </div>
          <div className="col-md-4 mb-4">
            <h5 className="text-primary">Role Based Access</h5>
            <p className="text-muted">AD / role-based access ensures correct visibility for reports.</p>
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
