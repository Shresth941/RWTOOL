// components/admin/Header/Header.js
import React from "react";
import "./Header.css";

export default function AdminHeader({ pageTitle, pageSubtitle }) {
  return (
    <header className="admin-header d-flex justify-content-between align-items-center px-3">
      <div className="d-flex align-items-center gap-3">
        <img src={process.env.PUBLIC_URL + "/sclogo.png"} alt="logo" style={{height:46}} />
        <div>
          <h4 className="m-0">{pageTitle || "Admin"}</h4>
          <small className="text-muted">{pageSubtitle || "Manage users, reports & permissions"}</small>
        </div>
      </div>

      <div className="d-flex align-items-center gap-3">
        <div className="user-avatar admin">SS</div>
      </div>
    </header>
  );
}
