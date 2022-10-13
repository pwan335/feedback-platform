import axios from "axios";

// axios
const request = axios.create({
    // userURL,
    baseURL: '/weather',
    withCredentials: true,
    timeout: 6000
});

export function weatherInfo(params) {
    return request({
        url: '',
        method: 'get',
        params,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
}