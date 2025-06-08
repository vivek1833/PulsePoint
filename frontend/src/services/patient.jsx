import axios from "axios";
import API_URL from "../utils/api";

// Get all patients
const getPatients = async (
  pageNumber = 0,
  pageSize = 10,
  sortColumn = "created_at",
  sortDirection = "asc"
) => {
  const token = localStorage.getItem("token");
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await axios.get(
    API_URL +
      `/patient?pageNumber=${pageNumber}&pageSize=${pageSize}&sortColumn=${sortColumn}&sortDirection=${sortDirection}`,
    { headers }
  );
  return response;
};

// Get patient by id
const getPatientById = async (id) => {
  const token = localStorage.getItem("token");
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await axios.get(API_URL + `/patient/${id}`, { headers });
  return response;
};

// Create patient
const createPatient = async (patient) => {
  const token = localStorage.getItem("token");
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await axios.post(API_URL + "/patient", patient, { headers });
  return response;
};

// Update patient
const updatePatient = async (id, patient) => {
  const token = localStorage.getItem("token");
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await axios.put(API_URL + `/patient/${id}`, patient, {
    headers,
  });
  return response;
};

// Delete patient
const deletePatient = async (id) => {
  const token = localStorage.getItem("token");
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await axios.delete(API_URL + `/patient/${id}`, { headers });
  return response;
};
export {
  getPatients,
  getPatientById,
  createPatient,
  updatePatient,
  deletePatient,
};
