// src/components/Ops/OpsSidebar.js
import React, { useState } from 'react';
import styles from './OpsSidebar.module.css';

const OpsSidebar = ({ onCategorySelect }) => {
    const [activeLink, setActiveLink] = useState('All'); 

    const handleLinkClick = (category) => {
        setActiveLink(category);
        onCategorySelect(category);
    };

    return (
        <nav className={styles.sidebar}>
            <div className={styles.navGroup}>
                <h3 className={styles.groupTitle}>SCB</h3>
                <a href="#" 
                   className={`${styles.navLink} ${activeLink === 'All' ? styles.active : ''}`}
                   onClick={() => handleLinkClick('All')}>
                   All Reports
                </a>
                <a href="#" 
                   className={`${styles.navLink} ${activeLink === 'General' ? styles.active : ''}`}
                   onClick={() => handleLinkClick('General')}>
                   General
                </a>
                <a href="#" 
                   className={`${styles.navLink} ${activeLink === 'Reports' ? styles.active : ''}`}
                   onClick={() => handleLinkClick('Reports')}>
                   Reports
                </a>
            </div>
            <div className={styles.navGroup}>
                <h3 className={styles.groupTitle}>Credit</h3>
                <a href="#" 
                   className={`${styles.navLink} ${activeLink === 'Credit Risk' ? styles.active : ''}`}
                   onClick={() => handleLinkClick('Credit Risk')}>
                   Credit Risk
                </a>
            </div>
        </nav>
    );
};

export default OpsSidebar;
