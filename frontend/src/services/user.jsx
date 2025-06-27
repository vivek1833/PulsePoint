import axios from "axios";
import API_URL from "../utils/api";

const USER_URL = API_URL + "/user";

const register = async (
  firstName,
  lastName,
  email,
  username,
  password,
  confirmPassword
) => {
  const response = await axios.post(USER_URL + "/register", {
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
  const response = await axios.post(USER_URL + "/login", {
    username,
    password,
  });
  return response;
};

const verifyOTP = async (email, otp) => {
  const response = await axios.post(USER_URL + "/verify-otp", {
    email,
    otp,
  });
  return response;
};

const resendOTP = async (email) => {
  const response = await axios.post(USER_URL + "/resend-otp", {
    email,
  });
  return response;
};

const getLoggedInUserDetails = async () => {
  const token = localStorage.getItem("token");
  const headers = {
    Authorization: `Bearer ${token}`,
  };
  const response = await axios.get(USER_URL + "/", { headers });

  return response;
};

const logout = async () => {
  const token = localStorage.getItem("token");
  const headers = {
    Authorization: `Bearer ${token}`,
  };
  const response = await axios.post(USER_URL + "/logout", {}, { headers });
  return response;
};

const updateUser = async (data) => {
  const token = localStorage.getItem("token");
  const headers = {
    Authorization: `Bearer ${token}`,
  };
  const response = await axios.post(USER_URL + "/", data, { headers });
  return response;
};

export { register, login, verifyOTP, resendOTP, getLoggedInUserDetails, logout, updateUser };
