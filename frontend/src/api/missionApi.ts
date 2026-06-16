import { apiClient } from './client';
import type { AssignmentResult, Mission } from '../types';

export const missionApi = {
  list: () => apiClient.get<Mission[]>('/missions').then((r) => r.data),
  get: (id: number) => apiClient.get<Mission>(`/missions/${id}`).then((r) => r.data),
  create: (mission: Mission) => apiClient.post<Mission>('/missions', mission).then((r) => r.data),
  update: (id: number, mission: Mission) => apiClient.put<Mission>(`/missions/${id}`, mission).then((r) => r.data),
  remove: (id: number) => apiClient.delete(`/missions/${id}`),
  assign: (id: number) => apiClient.post<AssignmentResult>(`/missions/${id}/assign`).then((r) => r.data),
};
