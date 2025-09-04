

import React, { useState } from 'react';
import { useOutletContext } from 'react-router-dom';
import { initialRules, reportTypes } from '../data/mockData'; // Using mock data for now
import { FiPlus, FiEdit2, FiTrash2, FiUploadCloud } from 'react-icons/fi';
import Card from '../components/Card/Card'; // Assuming you have a Card component
import RuleModal from '../components/Modals/RuleModal'; // Assuming you have a RuleModal
import { uploadReport } from '../api/adminApi'; // <-- 1. IMPORT THE NEW API FUNCTION

const generateId = () => `id_${Date.now()}_${Math.random().toString(36).substr(2, 9)}`;

const ReportsPage = () => {
    const { setNotification } = useOutletContext();

    // --- State from your existing code ---
    const [rules, setRules] = useState(initialRules);
    const [activeTab, setActiveTab] = useState('config');
    const [currentRule, setCurrentRule] = useState({ id: null, name: '', path: '' });
    const [showRuleModal, setShowRuleModal] = useState(false);
    const [file, setFile] = useState(null);
    const [isUploading, setIsUploading] = useState(false);

    // --- Functions from your existing code (unchanged) ---
    const handleAddRule = () => {
        setCurrentRule({ id: null, name: '', path: '' });
        setShowRuleModal(true);
    };

    const handleEditRule = (rule) => {
        setCurrentRule(rule);
        setShowRuleModal(true);
    };

    const handleDeleteRule = (ruleId) => {
        if (window.confirm('Are you sure you want to delete this report rule?')) {
            setRules((prev) => prev.filter((r) => r.id !== ruleId));
            setNotification({ type: 'success', message: 'Report rule deleted successfully!' });
        }
    };

    const handleSaveRule = (ruleToSave) => {
        if (ruleToSave.id) {
            setRules((prev) => prev.map((r) => (r.id === ruleToSave.id ? ruleToSave : r)));
            setNotification({ type: 'success', message: 'Report rule updated successfully!' });
        } else {
            setRules((prev) => [...prev, { ...ruleToSave, id: generateId() }]);
            setNotification({ type: 'success', message: 'Report rule added successfully!' });
        }
        setShowRuleModal(false);
    };
    
    const handleFileChange = (e) => {
        if (e.target.files && e.target.files.length > 0) {
            setFile(e.target.files[0]);
        }
    };

    // --- 2. THE handleUpload FUNCTION IS NOW FULLY UPDATED ---
    const handleUpload = async () => {
        if (!file) {
            setNotification({ type: 'error', message: 'Please select a file to upload.' });
            return;
        }

        setIsUploading(true);
        try {
            // In a real app, this token would be stored securely after login.
            // For now, you can get it from Postman and paste it here for testing.
            const authToken = "PASTE_YOUR_JWT_TOKEN_HERE_FOR_TESTING"; 

            // Call the API function from adminApi.js
            const result = await uploadReport(file, authToken);
            
            // Use the actual filename from the backend response in the success message
            setNotification({ type: 'success', message: `File '${result.fileName}' uploaded successfully!` });
            
            setFile(null); // Clear the file input

        } catch (error) {
            // Display the specific error message from the backend
            setNotification({ type: 'error', message: error.message });
        } finally {
            setIsUploading(false); // Ensure the loading state is turned off
        }
    };

    const cardHeader = (
        <ul className="nav nav-tabs card-header-tabs">
            <li className="nav-item">
                <a className={`nav-link ${activeTab === 'config' ? 'active' : ''}`} href="#" onClick={() => setActiveTab('config')}>
                    Automated Path Config
                </a>
            </li>
            <li className="nav-item">
                <a className={`nav-link ${activeTab === 'upload' ? 'active' : ''}`} href="#" onClick={() => setActiveTab('upload')}>
                    Manual Upload
                </a>
            </li>
        </ul>
    );

    return (
        <div>
            <Card header={cardHeader}>
                <div className="page-content">
                    {activeTab === 'config' && (
                        <div>
                            <button className="btn btn-primary mb-3" onClick={handleAddRule}>
                                <FiPlus className="me-1" /> Add New Report
                            </button>
                            <table className="table table-hover align-middle">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Report Name</th>
                                        <th>Source Path</th>
                                        <th className="text-end">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {rules.map((rule) => (
                                        <tr key={rule.id}>
                                            <td><code>{rule.id.substring(0, 8)}</code></td>
                                            <td>{rule.name}</td>
                                            <td><code>{rule.path}</code></td>
                                            <td className="text-end">
                                                <button className="btn btn-sm btn-outline-secondary me-2" onClick={() => handleEditRule(rule)}>
                                                    <FiEdit2 />
                                                </button>
                                                <button className="btn btn-sm btn-outline-danger" onClick={() => handleDeleteRule(rule.id)}>
                                                    <FiTrash2 />
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    )}

                    {activeTab === 'upload' && (
                        <div>
                            <p className="text-muted">This tool is for uploading individual reports for exceptional cases.</p>
                            
                            {/* --- 3. THE UI IS NOW SIMPLER --- */}
                            {/* The dropdown for selecting report type has been removed. */}
                            <div className="mb-3">
                                <label className="form-label">Step 1: Choose File to Upload</label>
                                <input type="file" className="form-control" onChange={handleFileChange} style={{width: "40%"}}/>
                                {file && <small className="form-text text-muted mt-1">Selected: {file.name}</small>}
                            </div>

                            <div className="mt-4">
                                <button className="btn btn-success" onClick={handleUpload} disabled={isUploading || !file}>
                                    {isUploading ? (
                                        <>
                                            <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                                            Uploading...
                                        </>
                                    ) : (
                                        <>
                                            <FiUploadCloud className="me-1" /> Upload Now
                                        </>
                                    )}
                                </button>
                            </div>
                        </div>
                    )}
                </div>
            </Card>

            <RuleModal
                show={showRuleModal}
                onHide={() => setShowRuleModal(false)}
                onSave={handleSaveRule}
                rule={currentRule}
                setRule={setCurrentRule}
            />
        </div>
    );
};

export default ReportsPage;

