import { apiClient } from './client';
import type { AssignmentMapEntry } from '../types';

export const assignmentApi = {
  listActive: () => apiClient.get<AssignmentMapEntry[]>('/assignments').then((r) => r.data),
};
