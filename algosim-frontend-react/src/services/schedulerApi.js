import axios from "axios";

const API_URL = "http://localhost:8080/api/scheduler";

export const runScheduler = async (request) => {
    const response = await axios.post(
        `${API_URL}/run`,
        request
    );

    return response.data;
};