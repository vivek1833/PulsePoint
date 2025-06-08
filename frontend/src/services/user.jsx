import axios from "axios";
import API_URL from "../utils/api";

const register = async (firstName, lastName, email, password, confirmPassword) => {
    const response = await axios.post(API_URL + "/register", {
        firstName,
        lastName,
        email,
        password,
        confirmPassword
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

export { register, login };

