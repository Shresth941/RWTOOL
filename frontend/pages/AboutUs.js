// src/pages/AboutUs.js
import React from "react";
import Header from "../components/layout/Header/Header";
import Footer from "../components/layout/Footer/Footer";
import "../components/AboutUs/Aboutus.css";

export default function AboutUs() {
  return (
    <>
      <Header />
      <div className="about-page-wrapper">
        <div className="about-hero">
          <div className="about-hero-overlay">
            <h1>Learn more about us</h1>
            <p>
              The RW Tool automates report ingestion, processing and access management for Wealth Operations.
              It improves operational speed, accuracy and auditability for report workflows.
            </p>
          </div>
        </div>

        <section className="about-container container py-5">
          <div className="about-inner">
            <h2 className="who-title">WHO WE ARE</h2>
            <p className="intro-text">
              We build secure, automated file processing flows that integrate with AD groups, publish files to
              structured output folders and notify users of new reports. The portal supports searching, bookmarking,
              downloading by date range and role-based access control.
            </p>

            <div className="row features-row mt-5">
              <div className="col-md-4 feature-col">
                <div className="feature-icon"></div>
                <h5 className="feature-title">Secure File Transfer</h5>
                <p className="feature-desc">Automated secure APIs move files from source to destination.</p>
              </div>
              <div className="col-md-4 feature-col">
                <div className="feature-icon"></div>
                <h5 className="feature-title">Smart Processing</h5>
                <p className="feature-desc">Files are routed and transformed based on admin mappings.</p>
              </div>
              <div className="col-md-4 feature-col">
                <div className="feature-icon"></div>
                <h5 className="feature-title">Role-Based Access</h5>
                <p className="feature-desc">Users get access to reports through AD group mappings.</p>
              </div>
            </div>
          </div>
        </section>
      </div>

      <Footer />
    </>
  );
}
