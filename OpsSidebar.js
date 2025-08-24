// src/components/Ops/OpsSidebar.js
import React from 'react';
import styles from './OpsSidebar.module.css';

const OpsSidebar = () => {
    // In a real app, this would come from an API or router
    const activeLink = 'General'; 

    return (
        <nav className={styles.sidebar}>
            <div className={styles.navGroup}>
                <h3 className={styles.groupTitle}>SCB</h3>
                <a href="#" className={`${styles.navLink} ${activeLink === 'General' ? styles.active : ''}`}>General</a>
                <a href="#" className={styles.navLink}>Reports</a>
            </div>
            <div className={styles.navGroup}>
                <h3 className={styles.groupTitle}>Credit</h3>
                <a href="#" className={styles.navLink}>Credit Risk</a>
            </div>
        </nav>
    );
};

export default OpsSidebar;
