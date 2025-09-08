// ReportSearchBar.js
import React from "react";

const ReportSearchBar = ({ q, onChange }) => (
  <div className="input-group mb-3">
    <input type="text" className="form-control" placeholder="Search reports..." value={q} onChange={e=>onChange(e.target.value)} />
    <button className="btn btn-outline-secondary">Search</button>
  </div>
);

export default ReportSearchBar;
