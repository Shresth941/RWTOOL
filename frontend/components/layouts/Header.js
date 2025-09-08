// components/layout/Header/Header.js
import React from "react";
import { Link } from "react-router-dom";
import "./Header.css";

export default function Header({ onToggleSidebar }) {
  return (
    <header className="app-header d-flex align-items-center justify-content-between px-3">
      <div className="left">
        <button
          className="btn btn-link sidebar-toggle d-md-none me-2"
          aria-label="Toggle sidebar"
          onClick={onToggleSidebar}
        >
          ☰
        </button>
        <Link to="/" className="brand d-flex align-items-center text-decoration-none">
          <img src={process.env.PUBLIC_URL + "/sclogo.png"} alt="logo" className="brand-logo" />
          <div className="brand-text ms-2">
            <h4 className="m-0">RW Tool</h4>
            <small className="text-muted">Report Workflow</small>
          </div>
        </Link>
      </div>

      <div className="right d-flex align-items-center">
        <nav className="d-none d-md-block">
          <Link to="/aboutus" className="nav-link d-inline-block">About</Link>
          <Link to="/faq" className="nav-link d-inline-block">FAQ</Link>
          <Link to="/contactus" className="nav-link d-inline-block">Contact</Link>
        </nav>

        <div className="user-info d-flex align-items-center ms-3">
          <div className="user-avatar">SS</div>
          <div className="ms-2 d-none d-md-block">
            <div className="user-name">Shresth Singh</div>
            <small className="text-muted">Ops</small>
          </div>
        </div>
      </div>
    </header>
  );
}
