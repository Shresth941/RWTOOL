// OpsHeader.js
import React from "react";
import styles from "./OpsHeader.module.css";

const OpsHeader = ({ onSearchChange, onDateFilter, onTypeFilter, onDownloadsClick, onFavoritesClick, activeDateFilter }) => {
  return (
    <header className={styles.header}>
      <div className={styles.topRow}>
        <div className={styles.tabs}>
          <button className="btn btn-link" onClick={onDownloadsClick}>Downloads</button>
          <button className="btn btn-link" onClick={onFavoritesClick}>Favorites</button>
        </div>
        <div className={styles.actions}>
          <button className="btn btn-sm btn-outline-secondary">Logout</button>
        </div>
      </div>
      <div className={styles.bottomRow}>
        <div className={styles.searchBar}>
          <input placeholder="Search reports..." onChange={e=>onSearchChange(e)} className="form-control" />
        </div>
        <div className={styles.filters}>
          <div className="btn-group btn-group-sm">
            <button className={`btn ${activeDateFilter === 'today' ? 'btn-primary' : 'btn-outline-secondary'}`} onClick={() => onDateFilter('today')}>Today</button>
            <button className={`btn ${activeDateFilter === 'week' ? 'btn-primary' : 'btn-outline-secondary'}`} onClick={() => onDateFilter('week')}>This Week</button>
            <button className={`btn ${activeDateFilter === 'month' ? 'btn-primary' : 'btn-outline-secondary'}`} onClick={() => onDateFilter('month')}>This Month</button>
          </div>
          <select className="form-select form-select-sm" onChange={onTypeFilter}>
            <option value="All">All Types</option>
            <option value="Pdf">PDF</option>
            <option value="Txt">TXT</option>
          </select>
        </div>
      </div>
    </header>
  );
};

export default OpsHeader;
