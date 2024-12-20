export function authHeaders() {
    let  user = localStorage.getItem('user');
    if (user && user.token) {
        return {Authorization: `Bearer ${user.token}`};
    } else {
        return {};
    }
}