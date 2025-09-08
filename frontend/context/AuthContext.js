// src/context/AuthContext.js
import React, { createContext, useContext, useEffect, useState } from 'react';
import axios from 'axios';
import axiosInstance from '../services/axiosInstance'; // ensure this exists and exports axios instance

const AuthContext = createContext();

/**
 * Keys used in localStorage
 */
const LS_TOKEN_KEY = 'rwtool_token';
const LS_USER_KEY = 'rwtool_user';

/**
 * Provide authentication state and helpers to components.
 * - Stores token & user in localStorage
 * - Adds axios interceptor (Authorization: Bearer <token>)
 * - Exposes login/logout, isAuthenticated helpers
 */
export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(() => localStorage.getItem(LS_TOKEN_KEY));
  const [user, setUser] = useState(() => {
    const raw = localStorage.getItem(LS_USER_KEY);
    return raw ? JSON.parse(raw) : null;
  });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // Attach Authorization header for axiosInstance
    const reqInterceptor = axiosInstance.interceptors.request.use(
      (config) => {
        const t = localStorage.getItem(LS_TOKEN_KEY);
        if (t) config.headers = { ...config.headers, Authorization: `Bearer ${t}` };
        return config;
      },
      (error) => Promise.reject(error)
    );

    const resInterceptor = axiosInstance.interceptors.response.use(
      (res) => res,
      (error) => {
        // Optionally handle 401 globally
        if (error?.response?.status === 401) {
          // clear auth on unauthorized
          clearAuth();
        }
        return Promise.reject(error);
      }
    );

    return () => {
      axiosInstance.interceptors.request.eject(reqInterceptor);
      axiosInstance.interceptors.response.eject(resInterceptor);
    };
  }, []);

  const saveAuth = (tkn, userObj) => {
    setToken(tkn);
    setUser(userObj || null);
    if (tkn) localStorage.setItem(LS_TOKEN_KEY, tkn);
    else localStorage.removeItem(LS_TOKEN_KEY);
    if (userObj) localStorage.setItem(LS_USER_KEY, JSON.stringify(userObj));
    else localStorage.removeItem(LS_USER_KEY);
  };

  const clearAuth = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem(LS_TOKEN_KEY);
    localStorage.removeItem(LS_USER_KEY);
  };

  /**
   * login - call backend to get token & user info
   * Expects backend `POST /auth/login` returning { token, user }
   */
  const login = async (username, password) => {
    setLoading(true);
    try {
      const res = await axiosInstance.post('/auth/login', { username, password });
      const { token: tkn, user: userObj } = res.data || {};
      if (!tkn) throw new Error('No token returned from login');
      saveAuth(tkn, userObj);
      setLoading(false);
      return { ok: true, token: tkn, user: userObj };
    } catch (err) {
      setLoading(false);
      clearAuth();
      return { ok: false, error: err?.response?.data?.message || err.message };
    }
  };

  const logout = () => {
    // optional: call backend logout endpoint if implemented
    clearAuth();
  };

  const isAuthenticated = () => !!token;

  return (
    <AuthContext.Provider
      value={{
        token,
        user,
        loading,
        login,
        logout,
        isAuthenticated,
        setUser: (u) => saveAuth(token, u),
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
export default AuthContext;
