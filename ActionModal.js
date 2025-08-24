// src/components/Ops/ActionModal.js
import React from 'react';
import styles from './ActionModal.module.css';

const ActionModal = ({ show, onClose, title, children }) => {
    if (!show) {
        return null;
    }

    return (
        <div className={styles.backdrop} onClick={onClose}>
            <div className={styles.modal} onClick={e => e.stopPropagation()}>
                <h3>{title}</h3>
                {children}
                <button className="btn btn-primary" onClick={onClose}>Close</button>
            </div>
        </div>
    );
};

export default ActionModal;
