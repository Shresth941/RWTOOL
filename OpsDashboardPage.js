// src/pages/OpsDashboardPage.js
import React, { useState, useEffect } from 'react';
import OpsSidebar from '../components/Ops/OpsSidebar';
import OpsHeader from '../components/Ops/OpsHeader';
import ActionModal from '../components/Ops/ActionModal';
import { initialOpsReports } from '../data/mockData';
import { FiFolder, FiDownload, FiStar, FiCheckSquare, FiSquare } from 'react-icons/fi';
import styles from './OpsDashboardPage.module.css';

const OpsDashboardPage = () => {
    const [reports, setReports] = useState([]);
    const [filteredReports, setFilteredReports] = useState([]);
    const [showModal, setShowModal] = useState(null);
    const [selectedReports, setSelectedReports] = useState([]);

    useEffect(() => {
        setReports(initialOpsReports);
        setFilteredReports(initialOpsReports);
    }, []);

    const favorites = reports.filter(r => r.isFavorite);

    const handleSearchChange = (event) => {
        const searchTerm = event.target.value.toLowerCase();
        const filtered = reports.filter(report =>
            report.title.toLowerCase().includes(searchTerm)
        );
        setFilteredReports(filtered);
        setSelectedReports([]); // Clear selection on search
    };

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

    const handleDownloadSelected = () => {
        alert(`Downloading ${selectedReports.length} report(s)...`);
        setSelectedReports([]);
    };

    const groupedReports = filteredReports.reduce((acc, report) => {
        (acc[report.category] = acc[report.category] || []).push(report);
        return acc;
    }, {});

    return (
        <div className={styles.dashboard}>
            <OpsSidebar />
            <div className={styles.mainContent}>
                <OpsHeader 
                    onDownloadsClick={() => setShowModal('downloads')}
                    onFavoritesClick={() => setShowModal('favorites')}
                    onSearchChange={handleSearchChange}
                />
                <main className={styles.contentArea}>
                    {Object.entries(groupedReports).map(([category, reportsInCategory]) => (
                        <section key={category}>
                            <div className={styles.folderHeader}>
                                <FiFolder />
                                <h2>{category}</h2>
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
                                            <td><FiDownload className={styles.downloadIcon} /></td>
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
                        <button onClick={handleDownloadSelected}><FiDownload /> Download Selected</button>
                        <button onClick={handleAddToFavorites}><FiStar /> Add to Favorites</button>
                    </div>
                )}
            </div>

            <ActionModal 
                show={showModal === 'downloads'} 
                onClose={() => setShowModal(null)}
                title="Download Confirmation"
            >
                <p>The download action is performed on selected files using the toolbar that appears at the bottom of the screen.</p>
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
