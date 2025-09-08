// Sidebar.js
import React from "react";
import { NavLink } from "react-router-dom";
import "./Sidebar.css";

const Sidebar = () => {
  return (
    <nav className="sidebar p-3">
      <div className="mb-3">
        <img src={process.env.PUBLIC_URL + "/sclogo.png"} alt="logo" style={{height:36}}/>
      </div>
      <ul className="nav flex-column">
        <li className="nav-item">
          <NavLink to="/admin/users" className="nav-link">Users</NavLink>
        </li>
        <li className="nav-item">
          <NavLink to="/admin/reports" className="nav-link">Reports</NavLink>
        </li>
        <li className="nav-item">
          <NavLink to="/admin/permissions" className="nav-link">Permissions</NavLink>
        </li>
      </ul>
    </nav>
  );
};

export default Sidebar;
