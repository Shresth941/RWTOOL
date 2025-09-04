// This is the base URL of your Spring Boot backend.
const API_BASE_URL = 'http://localhost:8080/api/admin';

/**
 * Uploads a report file to the backend using the "intelligent upload" endpoint.
 *
 * @param {File} file - The file selected by the user.
 * @returns {Promise<object>} The JSON response from the server (the new report record).
 */
export const uploadReport = async (file) => {
    // FormData is the required format for sending files in an HTTP request.
    const formData = new FormData();
    formData.append('file', file);

    const response = await fetch(`${API_BASE_URL}/files/upload`, {
        method: 'POST',
        // This 'credentials: include' is CRITICAL for simple, cookie-based authentication.
        // It tells the browser to automatically send the session cookie with the request.
        credentials: 'include', 
        body: formData,
    });

    // If the response is not successful, parse the error message from the backend.
    if (!response.ok) {
        const errorData = await response.json().catch(() => ({ error: 'Failed to upload file.' }));
        throw new Error(errorData.error || 'An unknown error occurred.');
    }

    // If successful, return the JSON data (the new report object).
    return await response.json();
};

