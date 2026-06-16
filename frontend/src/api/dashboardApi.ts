import { apiClient } from './client';
import type { DashboardStats } from '../types';

export const dashboardApi = {
  stats: () => apiClient.get<DashboardStats>('/dashboard/stats').then((r) => r.data),
};
