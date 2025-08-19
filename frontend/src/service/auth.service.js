import { http } from './config.js'

export const authService = {
  login: (data, provider) => {
    return http.post(`v1/auth/login?provider=${provider}`, data)
  },

  register: (data) => {
    return http.post('v1/auth/register', data)
  },

  sendOTP: (data, type) => {
    return http.post(`v1/auth/send-otp?type=${type}`, data)
  },

  restPassword: (data) => {
    return http.post('v1/auth/reset-password', data)
  },

  introspect: (data) => {
    return http.post('v1/auth/introspect', data)
  }
};