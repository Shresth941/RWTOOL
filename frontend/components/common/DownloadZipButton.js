// DownloadZipButton.js
import React, { useState } from "react";
import reportsService from "../../services/reportsService";

const DownloadZipButton = ({ from, to, onComplete }) => {
  const [loading, setLoading] = useState(false);

  const handleDownload = async () => {
    setLoading(true);
    try {
      // reportsService.downloadZip should return { data: Blob }
      const resp = await reportsService.downloadZip({ from, to });
      const blob = new Blob([resp.data], { type: resp.headers["content-type"] || "application/zip" });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = `reports_${from || "all"}_${to || "all"}.zip`;
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
      if (onComplete) onComplete();
    } catch (err) {
      console.error("Download error", err);
      alert("Failed to download ZIP. See console.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <button className="btn btn-outline-primary" onClick={handleDownload} disabled={loading}>
      {loading ? <span className="spinner-border spinner-border-sm" /> : "Download ZIP"}
    </button>
  );
};

export default DownloadZipButton;
