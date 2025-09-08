// src/context/UIContext.js
import React, { createContext, useContext, useMemo, useState } from 'react';

/**
 * Simple UI context for toasts and app-level transient notifications.
 * Components can call showToast({ message, type }) to display a toast.
 *
 * Toast component is not provided here; integrate with your Toast/Toast.module.css
 * Example toast types: 'success' | 'error' | 'info'
 */

const UIContext = createContext();

export const UIProvider = ({ children }) => {
  const [toasts, setToasts] = useState([]); // each toast: { id, message, type, timeoutMs }

  const showToast = ({ message, type = 'info', timeoutMs = 3500 }) => {
    const id = `toast_${Date.now()}_${Math.random().toString(36).slice(2, 8)}`;
    setToasts((t) => [...t, { id, message, type, timeoutMs }]);

    // auto remove
    setTimeout(() => {
      setToasts((t) => t.filter((x) => x.id !== id));
    }, timeoutMs + 200); // small safety buffer
  };

  const removeToast = (id) => setToasts((t) => t.filter((x) => x.id !== id));

  const value = useMemo(
    () => ({
      toasts,
      showToast,
      removeToast,
    }),
    [toasts]
  );

  return <UIContext.Provider value={value}>{children}</UIContext.Provider>;
};

export const useUI = () => useContext(UIContext);
export default UIContext;
