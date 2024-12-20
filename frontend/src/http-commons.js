import axios from "axios";

export const AXIOS = axios.create({
    baseURL: 'http://localhost:8080/api/auth',
    headers: {
        'Access-Control-Allow-Origin': ['http://localhost:8080', 'http://localhost:8081'],
        'Access-Control-Allow-Methods': 'GET, POST, OPTIONS, PUT, PATCH, DELETE',
        'Access-Control-Allow-Headers': '*',
        'Access-Control-Allow-Credentials': true,
    }
})