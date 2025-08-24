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
            // Search filter
            if (searchTerm && !report.title.toLowerCase().includes(searchTerm.toLowerCase())) {
                return false;
            }
            // Category filter (from sidebar)
            if (categoryFilter !== 'All' && report.category !== categoryFilter) {
                return false;
            }
            // File type filter
            if (typeFilter !== 'All' && report.type !== typeFilter) {
                return false;
            }
            // Date filter
            if (dateFilter !== 'All') {
                const reportDate = new Date(report.date);
                const now = new Date();
                if (dateFilter === 'today') {
                    if (reportDate.toDateString() !== now.toDateString()) return false;
                }
                if (dateFilter === 'week') {
                    const oneWeekAgo = new Date(now.setDate(now.getDate() - 7));
                    if (reportDate < oneWeekAgo) return false;
                }
                if (dateFilter === 'month') {
                    if (reportDate.getMonth() !== now.getMonth() || reportDate.getFullYear() !== now.getFullYear()) return false;
                }
                if (dateFilter === 'year') {
                    if (reportDate.getFullYear() !== now.getFullYear()) return false;
                }
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

    const handleDownload = (reportIds) => {
        const reportsToDownload = reports.filter(r => reportIds.includes(r.id));
        alert(`Downloading ${reportsToDownload.length} report(s):\n${reportsToDownload.map(r => r.title).join('\n')}`);
        setSelectedReports([]);
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
                    onDownloadsClick={() => handleDownload(selectedReports)}
                    onFavoritesClick={() => setShowModal('favorites')}
                    onSearchChange={(e) => setSearchTerm(e.target.value)}
                    onDateFilter={handleDateFilterClick}
                    onTypeFilter={(e) => setTypeFilter(e.target.value)}
                    activeDateFilter={dateFilter}
                />
                <main className={styles.contentArea}>
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
