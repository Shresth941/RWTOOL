// src/pages/OpsDashboardPage.js
import React, { useState, useEffect, useMemo } from 'react';
import OpsSidebar from '../components/Ops/OpsSidebar';
import OpsHeader from '../components/Ops/OpsHeader';
import ActionModal from '../components/Ops/ActionModal';
import { initialOpsReports } from '../data/mockData';
import { FiFolder, FiDownload, FiStar } from 'react-icons/fi';
import styles from './OpsDashboardPage.module.css';

const OpsDashboardPage = () => {
    const [reports, setReports] = useState([]);
    const [showModal, setShowModal] = useState(null);
    const [selectedReports, setSelectedReports] = useState([]);
    
    // NEW: State to track downloaded files
    const [downloadHistory, setDownloadHistory] = useState([]);
    
    // State for filters
    const [searchTerm, setSearchTerm] = useState('');
    const [categoryFilter, setCategoryFilter] = useState('All');
    const [dateFilter, setDateFilter] = useState('All');
    const [typeFilter, setTypeFilter] = useState('All');

    useEffect(() => {
        setReports(initialOpsReports);
    }, []);

    const favorites = reports.filter(r => r.isFavorite);

    // Memoized filtering logic for performance
    const filteredReports = useMemo(() => {
        return reports.filter(report => {
            if (searchTerm && !report.title.toLowerCase().includes(searchTerm.toLowerCase())) return false;
            if (categoryFilter !== 'All' && report.category !== categoryFilter) return false;
            if (typeFilter !== 'All' && report.type !== typeFilter) return false;
            if (dateFilter !== 'All') {
                const reportDate = new Date(report.date);
                const now = new Date();
                if (dateFilter === 'today' && reportDate.toDateString() !== now.toDateString()) return false;
                if (dateFilter === 'week') {
                    const oneWeekAgo = new Date(new Date().setDate(now.getDate() - 7));
                    if (reportDate < oneWeekAgo) return false;
                }
                if (dateFilter === 'month' && (reportDate.getMonth() !== now.getMonth() || reportDate.getFullYear() !== now.getFullYear())) return false;
                if (dateFilter === 'year' && reportDate.getFullYear() !== now.getFullYear()) return false;
            }
            return true;
        });
    }, [reports, searchTerm, categoryFilter, dateFilter, typeFilter]);

    const handleSelectAll = (e) => {
        if (e.target.checked) {
            setSelectedReports(filteredReports.map(r => r.id));
        } else {
            setSelectedReports([]);
        }
    };

    const handleSelectOne = (e, id) => {
        if (e.target.checked) {
            setSelectedReports(prev => [...prev, id]);
        } else {
            setSelectedReports(prev => prev.filter(reportId => reportId !== id));
        }
    };

    const handleAddToFavorites = () => {
        setReports(prevReports =>
            prevReports.map(report =>
                selectedReports.includes(report.id) ? { ...report, isFavorite: true } : report
            )
        );
        alert(`${selectedReports.length} report(s) added to favorites!`);
        setSelectedReports([]);
    };

    // UPDATED: This function now updates the download history
    const handleDownload = (reportIds) => {
        const reportsToDownload = reports.filter(r => reportIds.includes(r.id));
        if (reportsToDownload.length === 0) {
            setShowModal('downloads'); // Open the modal even if no files are selected
            return;
        }

        // Add newly downloaded files to the top of the history
        setDownloadHistory(prevHistory => [...reportsToDownload, ...prevHistory]);
        
        alert(`Downloading ${reportsToDownload.length} report(s):\n${reportsToDownload.map(r => r.title).join('\n')}`);
        setSelectedReports([]);
        setShowModal('downloads'); // Show the modal with the updated list
    };
    
    const handleDateFilterClick = (filter) => {
        setDateFilter(prev => prev === filter ? 'All' : filter);
    };

    const groupedReports = filteredReports.reduce((acc, report) => {
        (acc[report.category] = acc[report.category] || []).push(report);
        return acc;
    }, {});

    return (
        <div className={styles.dashboard}>
            <OpsSidebar onCategorySelect={setCategoryFilter} />
            <div className={styles.mainContent}>
                <OpsHeader 
                    onDownloadsClick={() => setShowModal('downloads')}
                    onFavoritesClick={() => setShowModal('favorites')}
                    onSearchChange={(e) => setSearchTerm(e.target.value)}
                    onDateFilter={handleDateFilterClick}
                    onTypeFilter={(e) => setTypeFilter(e.target.value)}
                    activeDateFilter={dateFilter}
                />
                <main className={styles.contentArea}>
                    {/* Table rendering logic remains the same */}
                    {Object.entries(groupedReports).map(([category, reportsInCategory]) => (
                        <section key={category}>
                            <div className={styles.folderHeader}>
                                <FiFolder />
                                <h2>{category} ({reportsInCategory.length})</h2>
                            </div>
                            <table className={styles.table}>
                                <thead>
                                    <tr>
                                        <th className={styles.checkboxCell}>
                                            <input 
                                                type="checkbox" 
                                                className="form-check-input"
                                                onChange={handleSelectAll}
                                                checked={selectedReports.length === filteredReports.length && filteredReports.length > 0}
                                            />
                                        </th>
                                        <th>Title</th>
                                        <th>Type</th>
                                        <th>Date</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {reportsInCategory.map(report => (
                                        <tr key={report.id}>
                                            <td className={styles.checkboxCell}>
                                                <input 
                                                    type="checkbox" 
                                                    className="form-check-input"
                                                    checked={selectedReports.includes(report.id)}
                                                    onChange={(e) => handleSelectOne(e, report.id)}
                                                />
                                            </td>
                                            <td>
                                                <div className={styles.titleCell}>{report.title}</div>
                                                <div className={styles.subtitleCell}>{report.subtitle}</div>
                                            </td>
                                            <td>{report.type}</td>
                                            <td>{report.date}</td>
                                            <td>
                                                <FiDownload 
                                                    className={styles.downloadIcon} 
                                                    onClick={() => handleDownload([report.id])}
                                                />
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </section>
                    ))}
                </main>

                {selectedReports.length > 0 && (
                    <div className={styles.selectionToolbar}>
                        <p>{selectedReports.length} item(s) selected</p>
                        <button onClick={() => handleDownload(selectedReports)}><FiDownload /> Download Selected</button>
                        <button onClick={handleAddToFavorites}><FiStar /> Add to Favorites</button>
                    </div>
                )}
            </div>

            {/* UPDATED: This modal now displays the download history */}
            <ActionModal 
                show={showModal === 'downloads'} 
                onClose={() => setShowModal(null)}
                title="Download History"
            >
                {downloadHistory.length > 0 ? (
                    <ul className={styles.modalContentList}>
                        {/* Shows up to the 10 most recent downloads */}
                        {downloadHistory.slice(0, 10).map((item, index) => <li key={`${item.id}-${index}`}>{item.title}</li>)}
                    </ul>
                ) : (
                    <p>You haven't downloaded any files yet.</p>
                )}
            </ActionModal>

            <ActionModal 
                show={showModal === 'favorites'} 
                onClose={() => setShowModal(null)}
                title="Your Favorite Reports"
            >
                {favorites.length > 0 ? (
                    <ul className={styles.modalContentList}>
                        {favorites.map(fav => <li key={fav.id}>{fav.title}</li>)}
                    </ul>
                ) : (
                    <p>You haven't added any reports to your favorites yet.</p>
                )}
            </ActionModal>
        </div>
    );
};

export default OpsDashboardPage;
