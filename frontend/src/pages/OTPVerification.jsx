import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "../styles/global.css";
import { verifyOTP, resendOTP } from "../services/user";
import Loading from "../components/common/Loading";

const OTPVerification = () => {
  const [otp, setOtp] = useState(["", "", "", "", "", ""]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [timer, setTimer] = useState(300); // 5 minutes in seconds
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state?.email;

  useEffect(() => {
    if (!email) {
      navigate("/register");
      return;
    }

    const interval = setInterval(() => {
      setTimer((prevTimer) => {
        if (prevTimer <= 1) {
          clearInterval(interval);
          return 0;
        }
        return prevTimer - 1;
      });
    }, 1000);

    return () => clearInterval(interval);
  }, [email, navigate]);

  const handleChange = (index, value) => {
    if (value.length > 1) return;

    const newOtp = [...otp];
    newOtp[index] = value;
    setOtp(newOtp);

    // Auto-focus next input
    if (value && index < 5) {
      const nextInput = document.querySelector(`input[name=otp-${index + 1}]`);
      if (nextInput) nextInput.focus();
    }
  };

  const handleKeyDown = (index, e) => {
    if (e.key === "Backspace" && !otp[index] && index > 0) {
      const prevInput = document.querySelector(`input[name=otp-${index - 1}]`);
      if (prevInput) prevInput.focus();
    }
  };

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins.toString().padStart(2, "0")}:${secs
      .toString()
      .padStart(2, "0")}`;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    const otpString = otp.join("");
    if (otpString.length !== 6) {
      setError("Please enter a valid 6-digit OTP");
      setLoading(false);
      return;
    }

    try {
      const response = await verifyOTP(email, otpString);
      if (response.status === 200) {
        navigate("/login", {
          state: { message: "Email verified successfully. Please login." },
        });
      }
    } catch (err) {
      setError("Invalid OTP. Please try again.");
    }
    setLoading(false);
  };

  const handleResendOTP = async () => {
    setLoading(true);
    try {
      const response = await resendOTP(email);
      if (response.status === 200) {
        setTimer(300);
        setError("");
      }
      setTimer(300);
      setError("");
    } catch (err) {
      setError("Failed to resend OTP. Please try again.");
    }
    setLoading(false);
  };

  if (loading) {
    return <Loading message="Verifying OTP..." />;
  }

  return (
    <div className="login-container">
      <form onSubmit={handleSubmit} className="login-form">
        <h1>Verify Your Email</h1>
        <p>Please enter the 6-digit code sent to {email}</p>

        {error && <div className="error-message">{error}</div>}

        <div className="otp-container">
          {otp.map((digit, index) => (
            <input
              key={index}
              type="text"
              name={`otp-${index}`}
              value={digit}
              onChange={(e) => handleChange(index, e.target.value)}
              onKeyDown={(e) => handleKeyDown(index, e)}
              maxLength={1}
              pattern="[0-9]"
              inputMode="numeric"
              required
            />
          ))}
        </div>

        <button type="submit" className="sign-in-btn">
          Verify Email
        </button>

        <div className="resend-container">
          <button
            type="button"
            className="resend-btn"
            onClick={handleResendOTP}
            disabled={timer > 0}>
            Resend OTP {timer > 0 && `(${formatTime(timer)})`}
          </button>
        </div>
      </form>
    </div>
  );
};

export default OTPVerification;
