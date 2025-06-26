import axios from "axios";
import API_URL from "../utils/api";

const PATIENT_URL = API_URL + "/patient";

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
    PATIENT_URL +
      `?pageNumber=${pageNumber}&pageSize=${pageSize}&sortColumn=${sortColumn}&sortDirection=${sortDirection}`,
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

  const response = await axios.get(PATIENT_URL + `/${id}`, { headers });
  return response;
};

// Create patient
const createPatient = async (patient) => {
  const token = localStorage.getItem("token");
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await axios.post(PATIENT_URL, patient, { headers });
  return response;
};

// Update patient
const updatePatient = async (id, patient) => {
  const token = localStorage.getItem("token");
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await axios.put(PATIENT_URL + `/${id}`, patient, {
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

  const response = await axios.delete(PATIENT_URL + `/${id}`, { headers });
  return response;
};
export {
  getPatients,
  getPatientById,
  createPatient,
  updatePatient,
  deletePatient,
};
