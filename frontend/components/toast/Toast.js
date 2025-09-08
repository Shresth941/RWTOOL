// Toast.js
import React, { useEffect } from "react";
import styles from "./Toast.module.css";

const Toast = ({ message, type = "success", onClose, duration = 3000 }) => {
  useEffect(() => { const t = setTimeout(onClose, duration); return () => clearTimeout(t); }, [onClose, duration]);
  const cls = type === "success" ? "bg-success" : "bg-danger";
  return (
    <div className={styles.container}>
      <div className={`toast show ${cls} text-white`}>
        <div className="d-flex">
          <div className="toast-body">{message}</div>
          <button type="button" className="btn-close btn-close-white me-2 m-auto" onClick={onClose}></button>
        </div>
      </div>
    </div>
  );
};
export default Toast;
