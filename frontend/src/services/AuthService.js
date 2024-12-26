import axios from 'axios';

const BASE_URL = "http://localhost:8080/api/auth";

class AuthService {

    constructor() {
        this.axios = axios.create({
            baseURL: BASE_URL,
            timeout: 1000,
        })
    }

    signUp(user) {
        user.roles.push('ROLE_USER')
        return axios.post(`${BASE_URL}/signUp`, user);
    }
    signIn(user) {
        return axios.post(`${BASE_URL}/signIn`, user);
    }
}
export default new AuthService();