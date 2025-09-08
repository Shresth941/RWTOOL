// src/services/usersService.js
import axios from "./axiosInstance";

const BASE = "/admin";

export const getAllUsers = async () => {
  const res = await axios.get(`${BASE}/getAllUsers`);
  return res.data;
};

export const getUser = async (id) => {
  const res = await axios.get(`${BASE}/getUser/${id}`);
  return res.data;
};

export const createUser = async (userPayload) => {
  const res = await axios.post(`${BASE}/addUser`, userPayload);
  return res.data;
};

export const updateUser = async (id, userPayload) => {
  const res = await axios.put(`${BASE}/updateUser/${id}`, userPayload);
  return res.data;
};

export const deleteUser = async (id) => {
  const res = await axios.delete(`${BASE}/${id}`);
  return res.data;
};

export default {
  getAllUsers,
  getUser,
  createUser,
  updateUser,
  deleteUser,
};
