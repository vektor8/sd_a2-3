import axios, { AxiosInstance } from 'axios';

export const customAxios: AxiosInstance = axios.create({
    baseURL: "http://localhost:8080",
});

customAxios.interceptors.request.use(
    async config => {
        const token = localStorage.getItem("access_token");
        config.headers = {
            'Authorization': `Bearer ${token}`,
            // 'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Headers': '*'
        }
        return config;
    },
    error => {
        Promise.reject(error)
    });

