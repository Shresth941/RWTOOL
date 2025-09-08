// LoadingSpinner.js
import React from "react";

const LoadingSpinner = ({ size = 40 }) => (
  <div className="d-flex justify-content-center align-items-center py-3">
    <div className="spinner-border" role="status" style={{ width: size, height: size }}>
      <span className="visually-hidden">Loading...</span>
    </div>
  </div>
);

export default LoadingSpinner;
