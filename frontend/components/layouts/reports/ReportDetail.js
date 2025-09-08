// ReportDetail.js
import React from "react";

const ReportDetail = ({ report }) => {
  if (!report) return <div>Select a report</div>;
  return (
    <div>
      <h4>{report.title || report.name}</h4>
      <p>Type: {report.type}</p>
      <p>FileName: {report.fileName || report.filePath}</p>
      <p>Created At: {report.createdAt}</p>
      <a href={report.filePath} className="btn btn-primary" download>Download</a>
    </div>
  );
};

export default ReportDetail;
