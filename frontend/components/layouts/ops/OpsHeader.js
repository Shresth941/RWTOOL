// components/ops/OpsHeader.js
import React from "react";
import { FiSearch, FiBell } from "react-icons/fi";
import "./OpsHeader.module.css"; // optional module file for more styles

export default function OpsHeader({ onSearchChange, onDateFilter, activeDateFilter }) {
  return (
    <div className="ops-header d-flex flex-column p-3">
      <div className="d-flex justify-content-between align-items-center mb-2">
        <div className="d-flex gap-2 align-items-center">
          <button className="btn btn-outline-secondary btn-sm">Downloads</button>
          <button className="btn btn-outline-secondary btn-sm">Favorites</button>
        </div>

        <div className="d-flex align-items-center gap-2">
          <div className="d-flex align-items-center search-box">
            <FiSearch />
            <input
              className="form-control form-control-sm ms-2"
              placeholder="Search reports..."
              onChange={(e) => onSearchChange?.(e.target.value)}
            />
          </div>
          <button className="btn btn-light btn-sm"><FiBell /></button>
        </div>
      </div>

      <div className="d-flex gap-2">
        <div className="btn-group btn-group-sm">
          <button className={`btn ${activeDateFilter === "today" ? "btn-primary" : "btn-outline-secondary"}`} onClick={() => onDateFilter("today")}>Today</button>
          <button className={`btn ${activeDateFilter === "week" ? "btn-primary" : "btn-outline-secondary"}`} onClick={() => onDateFilter("week")}>This Week</button>
          <button className={`btn ${activeDateFilter === "month" ? "btn-primary" : "btn-outline-secondary"}`} onClick={() => onDateFilter("month")}>This Month</button>
          <button className={`btn ${activeDateFilter === "year" ? "btn-primary" : "btn-outline-secondary"}`} onClick={() => onDateFilter("year")}>This Year</button>
        </div>

        <select className="form-select form-select-sm ms-auto" style={{ width: 140 }}>
          <option>All Types</option>
          <option>Pdf</option>
          <option>Txt</option>
        </select>
      </div>
    </div>
  );
}

