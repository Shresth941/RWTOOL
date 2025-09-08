// components/ops/OpsDashboardPage.js
import React, { useState, useEffect } from "react";
import OpsHeader from "../ops/OpsHeader";
import OpsSidebar from "../layout/Sidebar/Sidebar";
import { FiFolder, FiDownload, FiStar } from "react-icons/fi";
import "./OpsDashboardPage.css"; // optional

// This is simplified to match your mock data approach.
// Replace fetching with axios calls to /api/reports as needed.

const mockReports = [
  { id: "r1", category: "General", title: "Account Statement Jan 2025", type: "Pdf", date: "2025-01-20", isFavorite: false },
  { id: "r2", category: "General", title: "Monthly Report Dec 2024", type: "Txt", date: "2024-12-31", isFavorite: true },
];

export default function OpsDashboardPage() {
  const [reports, setReports] = useState([]);
  const [selected, setSelected] = useState([]);
  const [filter, setFilter] = useState("All");

  useEffect(() => { setReports(mockReports); }, []);

  const toggleSelect = (id) => {
    setSelected(s => s.includes(id) ? s.filter(x => x !== id) : [...s, id]);
  };

  return (
    <div className="ops-dashboard d-flex">
      <OpsSidebar />
      <div className="main flex-grow-1">
        <OpsHeader onSearchChange={(q) => console.log("search", q)} onDateFilter={(d) => setFilter(d)} activeDateFilter={filter} />

        <main className="p-3">
          {["General","Credit Risk","Reports"].map(section => {
            const items = reports.filter(r => r.category === section);
            if (!items.length) return null;
            return (
              <section key={section} className="mb-4">
                <div className="d-flex align-items-center mb-2">
                  <FiFolder className="me-2" />
                  <h5 className="m-0">{section} ({items.length})</h5>
                </div>

                <table className="table table-hover">
                  <thead><tr><th></th><th>Title</th><th>Type</th><th>Date</th><th></th></tr></thead>
                  <tbody>
                    {items.map(r => (
                      <tr key={r.id}>
                        <td><input type="checkbox" checked={selected.includes(r.id)} onChange={() => toggleSelect(r.id)} /></td>
                        <td>{r.title}</td>
                        <td>{r.type}</td>
                        <td>{r.date}</td>
                        <td className="text-end">
                          <button className="btn btn-sm btn-outline-primary me-2" onClick={() => alert("Download " + r.title)}><FiDownload /></button>
                          <button className="btn btn-sm btn-outline-secondary" onClick={() => alert("Favorite")}><FiStar /></button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </section>
            );
          })}
        </main>
      </div>
    </div>
  );
}

