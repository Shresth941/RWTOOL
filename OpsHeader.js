// src/components/Ops/OpsHeader.js
import React from 'react';
import { FiSearch, FiBell, FiUser, FiDownload, FiUpload } from 'react-icons/fi';
import styles from './OpsHeader.module.css';

const OpsHeader = ({ onDownloadsClick, onFavoritesClick, onSearchChange }) => {
    return (
        <header className={styles.header}>
            <div className={styles.topRow}>
                <div className={styles.tabs}>
                    <button onClick={onDownloadsClick}>Downloads</button>
                    <button onClick={onFavoritesClick} className={styles.active}>Favorites</button>
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
                    <input type="text" placeholder="Search" onChange={onSearchChange} />
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
