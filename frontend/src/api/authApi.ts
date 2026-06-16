import { apiClient } from './client';

export interface AuthResponse {
  token: string;
  username: string;
}

export const authApi = {
  login: (username: string, password: string) =>
    apiClient.post<AuthResponse>('/auth/login', { username, password }).then((r) => r.data),
  register: (username: string, password: string) =>
    apiClient.post<AuthResponse>('/auth/register', { username, password }).then((r) => r.data),
};
