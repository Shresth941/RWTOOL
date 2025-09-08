// src/services/reportsService.js
import axios from "./axiosInstance";

/**
 * Endpoints:
 * GET /api/reports?page=0&size=20
 * GET /api/reports/search?q=...
 * GET /api/reports/filter?from=YYYY-MM-DD&to=YYYY-MM-DD&page=0&size=20
 * POST /api/reports (metadata)
 * PUT /api/reports/{id}
 * DELETE /api/reports/{id}
 * GET /api/reports/download?from=...&to=... (zip stream)
 */

const BASE = "/api/reports";

export const listReports = async ({ page = 0, size = 20, sort } = {}) => {
  const params = { page, size };
  if (sort) params.sort = sort;
  const res = await axios.get(BASE, { params });
  return res.data;
};

export const searchReports = async ({ q, page = 0, size = 20 }) => {
  const res = await axios.get(`${BASE}/search`, { params: { q, page, size } });
  return res.data;
};

export const filterReportsByDate = async ({ from, to, page = 0, size = 20 }) => {
  const res = await axios.get(`${BASE}/filter`, {
    params: { from, to, page, size },
  });
  return res.data;
};

export const createReport = async (payload) => {
  const res = await axios.post(BASE, payload);
  return res.data;
};

export const updateReport = async (id, payload) => {
  const res = await axios.put(`${BASE}/${id}`, payload);
  return res.data;
};

export const deleteReport = async (id) => {
  const res = await axios.delete(`${BASE}/${id}`);
  return res.data;
};

/**
 * Stream a zip for the given range. This will return a Blob
 * Caller should use browser download helper or anchor download.
 */
export const downloadRangeZip = async ({ from, to, reportType, fileName = "reports.zip" }) => {
  const res = await axios.get(`${BASE}/download`, {
    params: { from, to, reportType },
    responseType: "blob",
  });
  return { blob: res.data, suggestedFileName: fileName };
};

/**
 * Helper to trigger client-side save of downloaded blob
 */
export const saveBlobAsFile = ({ blob, filename }) => {
  const url = window.URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = filename || "download.zip";
  document.body.appendChild(a);
  a.click();
  a.remove();
  window.URL.revokeObjectURL(url);
};

export default {
  listReports,
  searchReports,
  filterReportsByDate,
  createReport,
  updateReport,
  deleteReport,
  downloadRangeZip,
  saveBlobAsFile,
};
