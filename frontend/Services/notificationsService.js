// src/services/notificationsService.js
import axios from "./axiosInstance";

const BASE = "/api/notifications";

export const getNotificationsForUser = async (userId) => {
  const res = await axios.get(`${BASE}/user/${userId}`);
  return res.data;
};

export const markNotificationRead = async (id) => {
  const res = await axios.post(`${BASE}/mark-read/${id}`);
  return res.data;
};

export default { getNotificationsForUser, markNotificationRead };
