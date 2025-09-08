// src/index.js
import React from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App"; // if your App imports routes.js then just render <App />
import RoutesConfig from "./routes"; // optional: if you prefer to render Routes directly here
import { AuthProvider } from "./context/AuthContext";
import { UIProvider } from "./context/UIContext";
import "./index.css";
import "bootstrap/dist/css/bootstrap.min.css";

/**
 * Two ways to mount routes:
 * 1) App contains <RoutesConfig /> -> render <App />
 * 2) App is a simple shell, you render <RoutesConfig /> here inside providers.
 *
 * This index uses App, expecting it to include the router/page layout.
 *
 * If App.js doesn't include routes, replace <App /> below with <RoutesConfig />.
 */

const container = document.getElementById("root");
const root = createRoot(container);

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <AuthProvider>
        <UIProvider>
          <App />
          {/* OR if App is not routing-aware, use: <RoutesConfig /> */}
        </UIProvider>
      </AuthProvider>
    </BrowserRouter>
  </React.StrictMode>
);
