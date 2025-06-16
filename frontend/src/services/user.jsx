import axios from "axios";
import API_URL from "../utils/api";

const register = async (
  firstName,
  lastName,
  email,
  username,
  password,
  confirmPassword
) => {
  const response = await axios.post(API_URL + "/register", {
    firstName,
    lastName,
    email,
    username,
    password,
    confirmPassword,
  });
  return response;
};

const login = async (username, password) => {
  const response = await axios.post(API_URL + "/login", {
    username,
    password,
  });
  return response;
};

const verifyOTP = async (email, otp) => {
  const response = await axios.post(API_URL + "/verify-otp", {
    email,
    otp,
  });
  return response;
};

const resendOTP = async (email) => {
  const response = await axios.post(API_URL + "/resend-otp", {
    email,
  });
  return response;
};

export { register, login, verifyOTP, resendOTP };
