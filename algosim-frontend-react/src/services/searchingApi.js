import axios from "axios";

const API_URL = "http://localhost:8080/api/searching";

export const runSearching = async (request) => {
    const response = await axios.post(
        `${API_URL}/run`,
        request
    );

    return response.data;
};