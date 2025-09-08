// components/modal/ActionModal.js
import React from "react";
import "./ActionModal.css";

export default function ActionModal({ show, onClose, title, children }) {
  if (!show) return null;
  return (
    <div className="am-backdrop" onClick={onClose}>
      <div className="am-modal" onClick={(e) => e.stopPropagation()} role="dialog" aria-modal="true">
        <div className="am-header d-flex justify-content-between align-items-center">
          <h5 className="m-0">{title}</h5>
          <button className="btn btn-link" onClick={onClose} aria-label="Close">✕</button>
        </div>
        <div className="am-body">{children}</div>
        <div className="am-footer text-end">
          <button className="btn btn-secondary me-2" onClick={onClose}>Close</button>
        </div>
      </div>
    </div>
  );
}
