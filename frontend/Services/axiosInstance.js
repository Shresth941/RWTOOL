// src/services/axiosInstance.js
import axios from "axios";

const BASE_URL = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";

const axiosInstance = axios.create({
  baseURL: BASE_URL,
  timeout: 30000,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

// Attach auth token if present (expects token stored in localStorage under 'authToken')
// If you use Basic auth, you can set Authorization header for each request instead.
axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem("authToken");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Simple response interceptor to unwrap errors in a consistent format
axiosInstance.interceptors.response.use(
  (res) => res,
  (error) => {
    // Normalize error object
    const payload = {
      message:
        error.response?.data?.message ||
        error.message ||
        "Unexpected API error",
      status: error.response?.status,
      data: error.response?.data,
    };
    return Promise.reject(payload);
  }
);

export default axiosInstance;
