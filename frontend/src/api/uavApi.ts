import { apiClient } from './client';
import type { Uav } from '../types';

export const uavApi = {
  list: () => apiClient.get<Uav[]>('/uavs').then((r) => r.data),
  get: (id: number) => apiClient.get<Uav>(`/uavs/${id}`).then((r) => r.data),
  create: (uav: Uav) => apiClient.post<Uav>('/uavs', uav).then((r) => r.data),
  update: (id: number, uav: Uav) => apiClient.put<Uav>(`/uavs/${id}`, uav).then((r) => r.data),
  remove: (id: number) => apiClient.delete(`/uavs/${id}`),
};
