import React from "react";
import "../../styles/global.css";

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="footer">
      <div className="footer-content">
        <div className="footer-section">
          <h3>PulsePoint</h3>
          <p>
            Transforming healthcare management through innovative solutions.
          </p>
        </div>

        <div className="footer-section">
          <h4>Quick Links</h4>
          <ul>
            <li>
              <a href="/">Home</a>
            </li>
            <li>
              <a href="/about">About Us</a>
            </li>
            <li>
              <a href="/contact">Contact</a>
            </li>
            <li>
              <a href="/privacy">Privacy Policy</a>
            </li>
          </ul>
        </div>

        <div className="footer-section">
          <h4>Contact Us</h4>
          <ul>
            <li>Email: info@pulsepoint.com</li>
            <li>Phone: (555) 123-4567</li>
            <li>Address: 123 Healthcare Ave</li>
          </ul>
        </div>
      </div>

      <div className="footer-bottom">
        <p>&copy; {currentYear} PulsePoint. All rights reserved.</p>
      </div>
    </footer>
  );
};

export default Footer;
