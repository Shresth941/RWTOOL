// ReportsPage.js
import React, { useEffect, useState } from "react";
import reportsService from "../../services/reportsService";
import LoadingSpinner from "../common/LoadingSpinner";
import PaginationControls from "../common/PaginationControls";
import DownloadZipButton from "../common/DownloadZipButton";

const ReportsPage = () => {
  const [reportsPage, setReportsPage] = useState({ content: [], totalElements: 0 });
  const [page, setPage] = useState(0);
  const size = 10;
  const [loading, setLoading] = useState(false);

  const load = async (p=0) => {
    setLoading(true);
    try {
      const resp = await reportsService.getPaginated(p, size);
      // expect { content:[], totalElements }
      setReportsPage(resp.data || resp);
      setPage(p);
    } catch (err) {
      alert("Failed to fetch reports");
    } finally {
      setLoading(false);
    }
  };

  useEffect(()=> { load(0); }, []);

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <h3>Report Config & Listing</h3>
      <div className="mb-2 d-flex justify-content-between">
        <DownloadZipButton />
      </div>
      <table className="table">
        <thead><tr><th>Id</th><th>Name</th><th>Path</th><th>Type</th></tr></thead>
        <tbody>
          {reportsPage.content.map(r => (
            <tr key={r.id}>
              <td>{r.id}</td>
              <td>{r.name}</td>
              <td><code>{r.path || r.filePath}</code></td>
              <td>{r.type}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <PaginationControls page={page} size={size} total={reportsPage.totalElements} onPageChange={load} />
    </div>
  );
};

export default ReportsPage;
