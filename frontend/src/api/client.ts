import axios from 'axios';

export const apiClient = axios.create({
  baseURL: '/api',
});

apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('uav_token');
  if (token) {
    config.headers = config.headers ?? {};
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// If the backend rejects the stored token (missing/expired/invalid), drop it
// and let App.tsx fall back to the login screen instead of showing a generic
// "Could not load..." error on every page.
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error?.response?.status === 401 || error?.response?.status === 403) {
      localStorage.removeItem('uav_token');
      localStorage.removeItem('uav_username');
      window.dispatchEvent(new Event('uav_auth_expired'));
    }
    return Promise.reject(error);
  },
);
