// components/homepage/Nav.js
import React from "react";
import { Link } from "react-router-dom";

export default function Nav() {
  return (
    <nav className="navbar navbar-expand-md navbar-light bg-white shadow-sm">
      <div className="container">
        <Link className="navbar-brand d-flex align-items-center" to="/">
          <img src={process.env.PUBLIC_URL + "/sclogo.png"} alt="logo" style={{height:40}} />
          <span className="ms-2">RW Tool</span>
        </Link>

        <div className="collapse navbar-collapse">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item"><Link to="/aboutus" className="nav-link">About</Link></li>
            <li className="nav-item"><Link to="/faq" className="nav-link">FAQ</Link></li>
            <li className="nav-item"><Link to="/contactus" className="nav-link">Contact</Link></li>
            <li className="nav-item"><Link to="/login" className="btn btn-outline-primary ms-3">Login</Link></li>
          </ul>
        </div>
      </div>
    </nav>
  );
}

