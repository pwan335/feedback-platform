import axios from "axios";

// axios
const request = axios.create({
    // userURL,
    baseURL: '/public',
    withCredentials: true,
    timeout: 6000
});

export function getCityByIP() {
    return request({
        url: '',
        method: 'get',
    })
}