import axios from "axios";

const API_URL = "http://localhost:8080/api/sorting";

export const runSorting = async (request) => {

    const response = await axios.post(
        `${API_URL}/visualize`,
        request
    );

    return response.data;
};