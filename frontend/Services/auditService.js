// src/services/auditService.js
import axios from "./axiosInstance";

const BASE = "/api/audit";

export const getAuditAll = async ({ page = 0, size = 50 } = {}) => {
  const res = await axios.get(BASE, { params: { page, size } });
  return res.data;
};

export const getAuditForUser = async (userId, { page = 0, size = 50 } = {}) => {
  const res = await axios.get(`${BASE}/user/${userId}`, { params: { page, size } });
  return res.data;
};

export default { getAuditAll, getAuditForUser };
