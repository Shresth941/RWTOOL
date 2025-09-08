// src/pages/NotFound.js
import React from "react";
import { Link } from "react-router-dom";
import Header from "../components/layout/Header/Header";
import Footer from "../components/layout/Footer/Footer";

export default function NotFound() {
  return (
    <>
      <Header />
      <main className="container text-center py-5">
        <h1 className="display-4">404</h1>
        <p className="lead">Sorry — the page you are looking for was not found.</p>
        <p>
          <Link to="/" className="btn btn-primary">Go to Home</Link>
        </p>
      </main>
      <Footer />
    </>
  );
}
