import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/global.css";

import {
  getPatients,
  createPatient,
  deletePatient,
  updatePatient,
} from "../services/patient";
import { getLoggedInUserDetails, logout } from "../services/user";
import Loading from "../components/common/Loading";
import Footer from "../components/common/Footer";

const Home = () => {
  const navigate = useNavigate();
  const [patients, setPatients] = useState([]);
  const [selectedPatient, setSelectedPatient] = useState(null);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");
  const [showAddModal, setShowAddModal] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [sortField, setSortField] = useState("createdAt");
  const [sortDirection, setSortDirection] = useState("asc");
  const [newPatient, setNewPatient] = useState({
    firstName: "",
    lastName: "",
    email: "",
    username: "",
    password: "",
    age: "",
    gender: "MALE",
    roomNo: "",
    condition: "STABLE",
    contactNo: "",
    diagnosis: "",
    admissionDate: new Date().toISOString().split("T")[0],
  });
  const [editMode, setEditMode] = useState(false);
  const [editedPatient, setEditedPatient] = useState(null);
  const [user, setUser] = useState(null);
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  useEffect(() => {
    setLoading(true);
    fetchPatients();
    getLoggedInUserDetails()
      .then((res) => setUser(res.data))
      .catch(() => navigate("/login"));
    setLoading(false);
  }, [currentPage, pageSize, sortField, sortDirection]);

  useEffect(() => {
    function handleClickOutside(event) {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setDropdownOpen(false);
      }
    }
    if (dropdownOpen) {
      document.addEventListener("mousedown", handleClickOutside);
    } else {
      document.removeEventListener("mousedown", handleClickOutside);
    }
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [dropdownOpen]);

  const fetchPatients = () => {
    setLoading(true);
    getPatients(currentPage, pageSize, sortField, sortDirection)
      .then((response) => {
        setPatients(response.data);
      })
      .catch((error) => {
        navigate("/login");
      });
    setLoading(false);
  };

  const handlePatientClick = (patient) => {
    setSelectedPatient(patient);
  };

  const handleCloseDetails = () => {
    setSelectedPatient(null);
  };

  const handleAddPatient = () => {
    setShowAddModal(true);
  };

  const handleCloseAddModal = () => {
    setShowAddModal(false);
    setNewPatient({
      firstName: "",
      lastName: "",
      email: "",
      username: "",
      password: "",
      age: "",
      gender: "MALE",
      admissionDate: new Date().toISOString().split("T")[0],
      condition: "STABLE",
      roomNo: "",
      contactNo: "",
      diagnosis: "",
    });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewPatient((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmitNewPatient = async (e) => {
    setLoading(true);
    e.preventDefault();
    try {
      await createPatient(newPatient);
      fetchPatients();
      handleCloseAddModal();
    } catch (error) {
      console.error("Error creating patient:", error);
      alert("Failed to create patient. Please try again.");
    }
    setLoading(false);
  };

  const filteredPatients = patients.filter(
    (patient) =>
      patient.firstName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      patient.lastName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      patient.roomNo.toLowerCase().includes(searchTerm.toLowerCase()) ||
      patient.condition.toLowerCase().includes(searchTerm.toLowerCase()) ||
      patient.admissionDate.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleDeletePatient = async (id) => {
    await deletePatient(id);
    fetchPatients();
    handleCloseDetails();
  };

  const handleEditClick = () => {
    setLoading(true);
    setEditMode(true);
    setEditedPatient({ ...selectedPatient });
    setLoading(false);
  };

  const handleCancelEdit = () => {
    setLoading(true);
    setEditMode(false);
    setEditedPatient(null);
    setLoading(false);
  };

  const handleEditInputChange = (e) => {
    const { name, value } = e.target;
    setEditedPatient((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSaveEdit = async () => {
    setLoading(true);
    try {
      const { patientId, ...rest } = editedPatient;
      await updatePatient(patientId, rest);
      setEditMode(false);
      setEditedPatient(null);
      fetchPatients();
      handleCloseDetails();
    } catch (error) {
      console.error("Error updating patient:", error);
      alert("Failed to update patient. Please try again.");
    }
    setLoading(false);
  };

  const handleSort = (field) => {
    if (sortField === field) {
      setSortDirection(sortDirection === "asc" ? "desc" : "asc");
    } else {
      setSortField(field);
      setSortDirection("asc");
    }
  };

  const getSortIcon = (field) => {
    if (sortField !== field) {
      return "↕️";
    }
    return sortDirection === "asc" ? "🔼" : "🔽";
  };

  const handleProfile = () => {
    navigate("/my-profile");
    setDropdownOpen(false);
  };

  const handleLogout = async () => {
    await logout();
    localStorage.removeItem("token");
    navigate("/login");
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="home-container">
      <div className="header">
        <h1>Hospital Admin Portal</h1>
        <div className="user-menu-container">
          {user && (
            <div ref={dropdownRef} className="user-menu-wrapper">
              <div
                className="user-menu-trigger"
                onClick={() => setDropdownOpen((open) => !open)}>
                <svg
                  height="36"
                  width="36"
                  viewBox="0 0 36 36"
                  className="user-avatar">
                  <circle cx="18" cy="12" r="7" fill="#bbb" />
                  <ellipse cx="18" cy="27" rx="10" ry="7" fill="#bbb" />
                </svg>
                <span className="user-name">
                  {user.firstName} {user.lastName}
                </span>
              </div>
              {dropdownOpen && (
                <div className="user-dropdown">
                  <div className="user-dropdown-item" onClick={handleProfile}>
                    Profile
                  </div>
                  <div
                    className="user-dropdown-item logout"
                    onClick={handleLogout}>
                    Logout
                  </div>
                </div>
              )}
            </div>
          )}
        </div>
      </div>

      <div className="search-and-add">
        <div className="search-bar">
          <input
            type="text"
            placeholder="Search by name, diagnosis, or room number..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <button className="add-patient-btn" onClick={handleAddPatient}>
          Add New Patient
        </button>
      </div>

      <div className="patients-list">
        <div className="list-header">
          <h2>Patients List</h2>
          <div className="page-size-selector">
            <label>Rows per page:</label>
            <select
              value={pageSize}
              onChange={(e) => setPageSize(Number(e.target.value))}>
              <option value="5">5</option>
              <option value="10">10</option>
              <option value="20">20</option>
              <option value="50">50</option>
            </select>
          </div>
        </div>

        <table>
          <thead>
            <tr>
              <th onClick={() => handleSort("firstName")}>
                Name {getSortIcon("firstName")}
              </th>
              <th onClick={() => handleSort("age")}>
                Age {getSortIcon("age")}
              </th>
              <th onClick={() => handleSort("roomNo")}>
                Room {getSortIcon("roomNo")}
              </th>
              <th onClick={() => handleSort("condition")}>
                Condition {getSortIcon("condition")}
              </th>
              <th onClick={() => handleSort("admissionDate")}>
                Admission Date {getSortIcon("admissionDate")}
              </th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {filteredPatients.map((patient) => (
              <tr key={patient.patientId}>
                <td>
                  {patient.firstName} {patient.lastName}
                </td>
                <td>{patient.age}</td>
                <td>{patient.roomNo}</td>
                <td>
                  <span className={`status ${patient.condition.toLowerCase()}`}>
                    {patient.condition}
                  </span>
                </td>
                <td>{patient.admissionDate}</td>
                <td>
                  <button
                    className="modal-btn primary"
                    onClick={() => handlePatientClick(patient)}>
                    View Details
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <div className="pagination">
          <button
            className="pagination-btn"
            onClick={() => setCurrentPage(currentPage - 1)}
            disabled={currentPage === 0}>
            Previous
          </button>
          <span className="page-info">Page {currentPage + 1}</span>
          <button
            className="pagination-btn"
            onClick={() => setCurrentPage(currentPage + 1)}
            disabled={filteredPatients.length < pageSize}>
            Next
          </button>
        </div>
      </div>

      {selectedPatient && (
        <div className="modal">
          <div className="modal-content">
            <div className="modal-header">
              <h2>Patient Details</h2>
              <button className="close-btn" onClick={handleCloseDetails}>
                &times;
              </button>
            </div>

            <div className="modal-body">
              <div className="patient-details">
                <p>
                  <strong>Name:</strong>
                  {editMode ? (
                    <div>
                      <input
                        type="text"
                        name="firstName"
                        value={editedPatient.firstName}
                        onChange={handleEditInputChange}
                      />
                      <input
                        type="text"
                        name="lastName"
                        value={editedPatient.lastName}
                        onChange={handleEditInputChange}
                      />
                    </div>
                  ) : (
                    `${selectedPatient.firstName} ${selectedPatient.lastName}`
                  )}
                </p>
                <p>
                  <strong>Age:</strong>
                  {editMode ? (
                    <input
                      type="number"
                      name="age"
                      value={editedPatient.age}
                      onChange={handleEditInputChange}
                    />
                  ) : (
                    selectedPatient.age
                  )}
                </p>
                <p>
                  <strong>Gender:</strong>
                  {editMode ? (
                    <select
                      name="gender"
                      value={editedPatient.gender}
                      onChange={handleEditInputChange}>
                      <option value="MALE">Male</option>
                      <option value="FEMALE">Female</option>
                      <option value="OTHER">Other</option>
                    </select>
                  ) : (
                    selectedPatient.gender
                  )}
                </p>
                <p>
                  <strong>Room Number:</strong>
                  {editMode ? (
                    <input
                      type="text"
                      name="roomNo"
                      value={editedPatient.roomNo}
                      onChange={handleEditInputChange}
                    />
                  ) : (
                    selectedPatient.roomNo
                  )}
                </p>
                <p>
                  <strong>Admission Date:</strong>
                  {editMode ? (
                    <input
                      type="date"
                      name="admissionDate"
                      value={editedPatient.admissionDate}
                      onChange={handleEditInputChange}
                    />
                  ) : (
                    selectedPatient.admissionDate
                  )}
                </p>
                <p>
                  <strong>Condition:</strong>
                  {editMode ? (
                    <select
                      name="condition"
                      value={editedPatient.condition}
                      onChange={handleEditInputChange}>
                      <option value="STABLE">Stable</option>
                      <option value="CRITICAL">Critical</option>
                      <option value="RECOVERING">Recovering</option>
                    </select>
                  ) : (
                    <span
                      className={`status ${selectedPatient.condition.toLowerCase()}`}>
                      {selectedPatient.condition}
                    </span>
                  )}
                </p>
                <p>
                  <strong>Contact:</strong>
                  {editMode ? (
                    <input
                      type="tel"
                      name="contactNo"
                      value={editedPatient.contactNo}
                      onChange={handleEditInputChange}
                    />
                  ) : (
                    selectedPatient.contactNo
                  )}
                </p>
                <p>
                  <strong>Diagnosis:</strong>
                  {editMode ? (
                    <textarea
                      name="diagnosis"
                      value={editedPatient.diagnosis}
                      onChange={handleEditInputChange}
                    />
                  ) : (
                    selectedPatient.diagnosis
                  )}
                </p>
              </div>
            </div>

            <div className="modal-footer">
              {editMode ? (
                <>
                  <button
                    className="modal-btn primary"
                    onClick={handleSaveEdit}>
                    Save Changes
                  </button>
                  <button
                    className="modal-btn secondary"
                    onClick={handleCancelEdit}>
                    Cancel
                  </button>
                </>
              ) : (
                <>
                  <button
                    className="modal-btn primary"
                    onClick={handleEditClick}>
                    Edit Patient
                  </button>
                  <button
                    className="modal-btn secondary"
                    onClick={() =>
                      handleDeletePatient(selectedPatient.patientId)
                    }>
                    Delete Patient
                  </button>
                </>
              )}
            </div>
          </div>
        </div>
      )}

      {showAddModal && (
        <div className="modal">
          <div className="modal-content">
            <div className="modal-header">
              <h2>Add New Patient</h2>
              <button className="close-btn" onClick={handleCloseAddModal}>
                &times;
              </button>
            </div>

            <div className="modal-body">
              <form onSubmit={handleSubmitNewPatient}>
                <div className="form-grid">
                  <div className="form-group">
                    <label>First Name:</label>
                    <input
                      type="text"
                      name="firstName"
                      value={newPatient.firstName}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>Last Name:</label>
                    <input
                      type="text"
                      name="lastName"
                      value={newPatient.lastName}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>Age:</label>
                    <input
                      type="number"
                      name="age"
                      value={newPatient.age}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>Gender:</label>
                    <select
                      name="gender"
                      value={newPatient.gender}
                      onChange={handleInputChange}
                      required>
                      <option value="MALE">Male</option>
                      <option value="FEMALE">Female</option>
                      <option value="OTHER">Other</option>
                    </select>
                  </div>
                  <div className="form-group">
                    <label>Room Number:</label>
                    <input
                      type="text"
                      name="roomNo"
                      value={newPatient.roomNo}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>Condition:</label>
                    <select
                      name="condition"
                      value={newPatient.condition}
                      onChange={handleInputChange}
                      required>
                      <option value="STABLE">Stable</option>
                      <option value="CRITICAL">Critical</option>
                      <option value="RECOVERING">Recovering</option>
                    </select>
                  </div>
                  <div className="form-group">
                    <label>Contact Number:</label>
                    <input
                      type="tel"
                      name="contactNo"
                      value={newPatient.contactNo}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>Admission Date:</label>
                    <input
                      type="date"
                      name="admissionDate"
                      value={newPatient.admissionDate}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-group full-width">
                    <label>Diagnosis:</label>
                    <textarea
                      name="diagnosis"
                      value={newPatient.diagnosis}
                      onChange={handleInputChange}
                    />
                  </div>
                </div>

                <div className="modal-footer">
                  <button type="submit" className="modal-btn primary">
                    Save Patient
                  </button>
                  <button
                    type="button"
                    className="modal-btn secondary"
                    onClick={handleCloseAddModal}>
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      )}

      {loading && <Loading />}
      <Footer />
    </div>
  );
};

export default Home;
