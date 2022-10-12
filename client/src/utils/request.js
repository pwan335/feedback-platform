import axios from "axios";

let baseURL = '/api';
// let userURL = ''

// axios
const request = axios.create({
    baseURL,
    // userURL,
    withCredentials: true,
    timeout: 6000
});

// interceptor for request
request.interceptors.request.use(
    config => {
        // add token
        const token = localStorage.getItem('token')
        if (token) {
          config.headers["Authorization"] = 'Bearer ' + token;
        }

        return config;
    },
    error => {
        // do something with request error
        console.log(error);
        return Promise.reject(error);
    }
);

// interceptor for response
request.interceptors.response.use(
    response => {
        return response.data;
    },
    error => {
        const data = error.response.data;
       // const status = error.response.status;

        // manage different status codes
        // if (status === 401) {
        //     console.log("token expired");
        // } else if (status === 500) {
        //     console.log("sever error");
        // }
        return Promise.resolve(data);
    }
);

export default request;