// src/services/authService.js
import axios from "./axiosInstance";

const AUTH_BASE = "/auth";

export const login = async ({ username, password }) => {
  // If your backend returns token and user
  const res = await axios.post(`${AUTH_BASE}/login`, { username, password });
  return res.data;
};

export const logout = async () => {
  // backend logout endpoint optional
  try {
    await axios.post(`${AUTH_BASE}/logout`);
  } catch (e) {
    // ignore
  }
  localStorage.removeItem("authToken");
};

export const getProfile = async () => {
  const res = await axios.get(`${AUTH_BASE}/me`);
  return res.data;
};

export default { login, logout, getProfile };
