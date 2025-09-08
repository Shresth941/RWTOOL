// ReportsList.js
import React from "react";
import { FiDownload, FiStar } from "react-icons/fi";

const ReportsList = ({ reports = [], onDownload, onToggleFavorite }) => {
  return (
    <div>
      <h4>Reports</h4>
      <table className="table">
        <thead><tr><th></th><th>Title</th><th>Type</th><th>Date</th><th></th></tr></thead>
        <tbody>
          {reports.map(r => (
            <tr key={r.id}>
              <td><input type="checkbox" /></td>
              <td>
                <div style={{fontWeight:600}}>{r.title || r.name}</div>
                <div className="text-muted">{r.subtitle || r.fileName}</div>
              </td>
              <td>{r.type}</td>
              <td>{r.date || r.createdAt}</td>
              <td className="text-end">
                <button className="btn btn-sm btn-outline-secondary me-2" onClick={()=> onDownload?.(r)}><FiDownload /></button>
                <button className="btn btn-sm btn-outline-warning" onClick={()=> onToggleFavorite?.(r)}><FiStar /></button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ReportsList;
