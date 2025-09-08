// src/services/userReportsService.js
import axios from "./axiosInstance";

/**
 * POST /api/user-reports — assign
 * GET /api/user-reports/user/{userId}/recent — recent list
 */

const BASE = "/api/user-reports";

export const assignUserReport = async (payload) => {
  const res = await axios.post(BASE, payload);
  return res.data;
};

export const getRecentForUser = async (userId, { limit = 20 } = {}) => {
  const res = await axios.get(`${BASE}/user/${userId}/recent`, { params: { limit } });
  return res.data;
};

export default { assignUserReport, getRecentForUser };
