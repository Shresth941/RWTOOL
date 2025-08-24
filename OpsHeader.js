// src/components/Ops/OpsHeader.js
import React from 'react';
import { FiSearch, FiBell, FiUser, FiDownload, FiUpload } from 'react-icons/fi';
import styles from './OpsHeader.module.css';

const OpsHeader = ({ onDownloadsClick, onFavoritesClick, onSearchChange, onDateFilter, onTypeFilter, activeDateFilter }) => {
    return (
        <header className={styles.header}>
            <div className={styles.topRow}>
                <div className={styles.tabs}>
                    <button onClick={onDownloadsClick}>Downloads</button>
                    <button onClick={onFavoritesClick}>Favorites</button>
                </div>
                <div className={styles.actions}>
                    <button className={styles.iconButton}><FiBell /></button>
                    <button className={styles.iconButton}><FiUser /></button>
                    <button className="btn btn-sm btn-outline-secondary logoutButton">Logout</button>
                </div>
            </div>
            <div className={styles.bottomRow}>
                <div className={styles.searchBar}>
                    <FiSearch />
                    <input type="text" placeholder="Search reports..." onChange={onSearchChange} />
                </div>
                <div className={styles.filters}>
                    <div className="btn-group btn-group-sm">
                        <button type="button" className={`btn ${activeDateFilter === 'today' ? 'btn-primary' : 'btn-outline-secondary'}`} onClick={() => onDateFilter('today')}>Today</button>
                        <button type="button" className={`btn ${activeDateFilter === 'week' ? 'btn-primary' : 'btn-outline-secondary'}`} onClick={() => onDateFilter('week')}>This Week</button>
                        <button type="button" className={`btn ${activeDateFilter === 'month' ? 'btn-primary' : 'btn-outline-secondary'}`} onClick={() => onDateFilter('month')}>This Month</button>
                        <button type="button" className={`btn ${activeDateFilter === 'year' ? 'btn-primary' : 'btn-outline-secondary'}`} onClick={() => onDateFilter('year')}>This Year</button>
                    </div>
                     <select className="form-select form-select-sm" onChange={onTypeFilter}>
                        <option value="All">All Types</option>
                        <option value="Pdf">PDF</option>
                        <option value="Txt">TXT</option>
                    </select>
                </div>
                <div className={styles.toolbar}>
                    <button className={styles.iconButton}><FiUpload /></button>
                    <button className={styles.iconButton}><FiDownload /></button>
                </div>
            </div>
        </header>
    );
};

export default OpsHeader;
