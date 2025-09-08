// src/services/ingestService.js
import axios from "./axiosInstance";

/**
 * POST /api/ingest/upload (multipart)
 * fields: file, uniqueId, action, fileType, outputFolderPath
 *
 * POST /api/ingest/process?sourcePath=...&destinationPath=...
 */

const BASE = "/api/ingest";

export const uploadFile = async ({ file, uniqueId, action, fileType, outputFolderPath }) => {
  const fd = new FormData();
  fd.append("file", file);
  if (uniqueId) fd.append("uniqueId", uniqueId);
  if (action) fd.append("action", action);
  if (fileType) fd.append("fileType", fileType);
  if (outputFolderPath) fd.append("outputFolderPath", outputFolderPath);

  const res = await axios.post(`${BASE}/upload`, fd, {
    headers: { "Content-Type": "multipart/form-data" },
  });
  return res.data;
};

export const triggerProcess = async ({ sourcePath, destinationPath }) => {
  const res = await axios.post(`${BASE}/process`, null, {
    params: { sourcePath, destinationPath },
  });
  return res.data;
};

export default { uploadFile, triggerProcess };
