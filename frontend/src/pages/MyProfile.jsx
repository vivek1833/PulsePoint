import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/global.css";
import Loading from "../components/common/Loading";
import { getLoggedInUserDetails, updateUser } from "../services/user";

const MyProfile = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [user, setUser] = useState(null);
  const [editMode, setEditMode] = useState(false);
  const [form, setForm] = useState({ firstName: "", lastName: "" });
  const [passwords, setPasswords] = useState({
    current: "",
    new: "",
    confirm: "",
  });
  const [msg, setMsg] = useState("");
  const [err, setErr] = useState("");
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    getLoggedInUserDetails()
      .then((response) => {
        setUser(response.data);
        setForm({
          firstName: response.data.firstName,
          lastName: response.data.lastName,
        });
        setLoading(false);
      })
      .catch(() => {
        navigate("/login");
      });
  }, [navigate]);

  const handleEdit = () => {
    setEditMode(true);
    setMsg("");
    setErr("");
    setPasswords({ current: "", new: "", confirm: "" });
  };

  const handleCancel = () => {
    setEditMode(false);
    setMsg("");
    setErr("");
    setForm({ firstName: user.firstName, lastName: user.lastName });
    setPasswords({ current: "", new: "", confirm: "" });
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handlePasswordChange = (e) => {
    setPasswords({ ...passwords, [e.target.name]: e.target.value });
  };

  const handleSave = async () => {
    setSaving(true);
    setMsg("");
    setErr("");
    // Validate password fields if any are filled
    if (passwords.current || passwords.new || passwords.confirm) {
      if (!passwords.current || !passwords.new || !passwords.confirm) {
        setErr("Please fill all password fields.");
        setSaving(false);
        return;
      }
      if (passwords.new !== passwords.confirm) {
        setErr("New passwords do not match.");
        setSaving(false);
        return;
      }
    }
    try {
      const payload = {
        id: user.id,
        firstName: form.firstName,
        lastName: form.lastName,
      };
      if (passwords.current && passwords.new && passwords.confirm) {
        payload.currentPassword = passwords.current;
        payload.password = passwords.new;
        payload.confirmPassword = passwords.confirm;
      }
      const res = await updateUser(payload);
      setMsg("Profile updated successfully.");
      setEditMode(false);
      setUser({ ...user, firstName: form.firstName, lastName: form.lastName });
    } catch (e) {
      setErr(e.response?.data || "Failed to update profile.");
    }
    setSaving(false);
  };

  if (loading) return <Loading />;
  if (!user) return null;

  return (
    <div className="profile-container">
      <div className="profile-card">
        <div className="profile-avatar-section">
          <svg
            height="80"
            width="80"
            viewBox="0 0 40 40"
            className="profile-avatar">
            <circle cx="20" cy="14" r="10" fill="#bdbdbd" />
            <ellipse cx="20" cy="32" rx="16" ry="10" fill="#e0e0e0" />
          </svg>
        </div>
        <div className="profile-details">
          {msg && <div style={{ color: "green", marginBottom: 8 }}>{msg}</div>}
          {err && <div style={{ color: "red", marginBottom: 8 }}>{err}</div>}
          {editMode ? (
            <>
              <div
                style={{
                  display: "flex",
                  gap: 8,
                  justifyContent: "center",
                  marginBottom: 12,
                }}>
                <input
                  type="text"
                  name="firstName"
                  value={form.firstName}
                  onChange={handleChange}
                  className="profile-edit-input"
                  placeholder="First Name"
                  style={{ width: 120 }}
                />
                <input
                  type="text"
                  name="lastName"
                  value={form.lastName}
                  onChange={handleChange}
                  className="profile-edit-input"
                  placeholder="Last Name"
                  style={{ width: 120 }}
                />
              </div>
              <div style={{ margin: "12px 0 0 0" }}>
                <input
                  type="password"
                  name="current"
                  value={passwords.current}
                  onChange={handlePasswordChange}
                  className="profile-edit-input"
                  placeholder="Current Password"
                  style={{ width: 250, marginBottom: 8 }}
                />
                <input
                  type="password"
                  name="new"
                  value={passwords.new}
                  onChange={handlePasswordChange}
                  className="profile-edit-input"
                  placeholder="New Password"
                  style={{ width: 250, marginBottom: 8 }}
                />
                <input
                  type="password"
                  name="confirm"
                  value={passwords.confirm}
                  onChange={handlePasswordChange}
                  className="profile-edit-input"
                  placeholder="Confirm New Password"
                  style={{ width: 250 }}
                />
              </div>
            </>
          ) : (
            <>
              <h2>
                {user.firstName} {user.lastName}
              </h2>
            </>
          )}
          <p>
            <strong>Email:</strong> {user.email}
          </p>
          <p>
            <strong>Username:</strong> {user.username}
          </p>
          <p>
            <strong>Type:</strong> {user.type}
          </p>
          <p>
            <strong>Account Created:</strong>{" "}
            {user.createdAt
              ? new Date(user.createdAt).toLocaleDateString()
              : "-"}
          </p>
        </div>
        {editMode ? (
          <div style={{ display: "flex", gap: 12, marginTop: 16 }}>
            <button
              className="modal-btn primary"
              onClick={handleSave}
              disabled={saving}>
              {saving ? "Saving..." : "Save"}
            </button>
            <button
              className="modal-btn secondary"
              onClick={handleCancel}
              disabled={saving}>
              Cancel
            </button>
          </div>
        ) : (
          <button
            className="modal-btn primary"
            style={{ marginTop: 24 }}
            onClick={handleEdit}>
            Edit
          </button>
        )}
        <button
          className="modal-btn secondary"
          style={{ marginTop: 12 }}
          onClick={() => navigate("/")}>
          Back to Home
        </button>
      </div>
    </div>
  );
};

export default MyProfile;
