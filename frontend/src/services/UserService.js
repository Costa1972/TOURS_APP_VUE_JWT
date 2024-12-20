import axios from 'axios';
import { authHeaders } from "@/jwt/authHeaders";

const BASE_URL = 'http://localhost:8080/api';

class UserService {

    constructor() {
        this.axios = axios.create({
            baseURL: BASE_URL,
            timeout: 1000,
            headers: {
                Authorization: `Bearer ${authHeaders.token}`
            }
        })
    }

    static getAllUsers() {
        return axios.get(`${BASE_URL}/users`);
    }

}

export default UserService;