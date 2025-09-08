// ActionModal.js
import React from "react";
import styles from "./ActionModal.module.css"; // you had a module earlier

const ActionModal = ({ show, onClose, title, children }) => {
  if (!show) return null;
  return (
    <div className={styles.backdrop} onClick={onClose}>
      <div className={styles.modal} onClick={e => e.stopPropagation()}>
        <h5>{title}</h5>
        <div>{children}</div>
        <div className="text-end mt-3">
          <button className="btn btn-secondary me-2" onClick={onClose}>Close</button>
        </div>
      </div>
    </div>
  );
};

export default ActionModal;
