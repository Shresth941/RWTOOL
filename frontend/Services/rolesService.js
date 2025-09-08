// src/services/rolesService.js
import axios from "./axiosInstance";

const BASE = "/api/roles";

export const getRoles = async () => {
  const res = await axios.get(BASE);
  return res.data;
};

export const createRole = async (rolePayload) => {
  const res = await axios.post(BASE, rolePayload);
  return res.data;
};

export default { getRoles, createRole };
