// Add this to your existing src/data/mockData.js file

export const initialOpsReports = [
    { 
        id: 'report_1', 
        category: 'General', 
        title: 'Account Statement - Jan 2025', 
        subtitle: 'Account Statement - Jan 2025', 
        type: 'Pdf', 
        date: '2025-01-20',
        isFavorite: false,
    },
    { 
        id: 'report_2', 
        category: 'General', 
        title: 'Monthly Report - Dec 2024', 
        subtitle: 'Monthly Report - Dec 2024', 
        type: 'Txt', 
        date: '2024-12-31',
        isFavorite: true, // Example of a pre-favorited item
    },
    { 
        id: 'report_3', 
        category: 'General', 
        title: 'Loan Summary 2024', 
        subtitle: 'Loan Summary 2024', 
        type: 'Pdf', 
        date: '2024-11-12',
        isFavorite: false,
    },
    { 
        id: 'report_4', 
        category: 'Credit Risk', 
        title: 'Credit Report - Oct', 
        subtitle: 'Credit Report - Oct', 
        type: 'Pdf', 
        date: '2024-10-05',
        isFavorite: false,
    },
    { 
        id: 'report_5', 
        category: 'Reports', 
        title: 'Q3 Earnings Report', 
        subtitle: 'Q3 Earnings Report 2024', 
        type: 'Pdf', 
        date: '2024-09-30',
        isFavorite: true, // Example of a pre-favorited item
    },
];
