import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/global.css";

const Error = () => {
  const navigate = useNavigate();

  return (
    <div className="error-container">
      <div className="error-content">
        <div className="error-code">404</div>
        <h1 className="error-title">Page Not Found</h1>
        <p className="error-message">
          Oops! The page you are looking for might have been removed, had its
          name changed, or is temporarily unavailable.
        </p>
        <div className="error-actions">
          <button onClick={() => navigate("/")} className="error-btn">
            Back to Home
          </button>
          <button onClick={() => navigate(-1)} className="error-btn secondary">
            Go Back
          </button>
        </div>
      </div>
    </div>
  );
};

export default Error;
