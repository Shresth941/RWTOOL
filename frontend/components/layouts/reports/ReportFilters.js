// ReportFilters.js
import React from "react";

const ReportFilters = ({ from, to, onFromChange, onToChange, onApply }) => (
  <div className="d-flex gap-2 align-items-center">
    <input type="date" className="form-control" value={from} onChange={e=>onFromChange(e.target.value)} />
    <input type="date" className="form-control" value={to} onChange={e=>onToChange(e.target.value)} />
    <button className="btn btn-primary" onClick={onApply}>Filter</button>
  </div>
);

export default ReportFilters;
