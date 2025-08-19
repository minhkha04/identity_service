import axios from 'axios'

const http = axios.create({
  baseURL: 'http://localhost:8080/identity/api/', // domain
  timeout: 30000,
  headers: {},
})

http.interceptors.request.use(
  function (config) {
    if (config.url.includes('auth') || config.url.includes('public')) {
      return config;
    }
    const token = JSON.parse(localStorage.getItem("token"));
    if (token) {
      config.headers.Authorization = "Bearer " + token;
    }
    return config;
  },
);

// Add a response interceptor
http.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    return Promise.reject(error);
  }
);

export { http }