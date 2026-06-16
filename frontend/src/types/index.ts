export type UavStatus = 'AVAILABLE' | 'ASSIGNED' | 'MAINTENANCE';
export type MissionPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
export type MissionType = 'SURVEILLANCE' | 'MAPPING' | 'DELIVERY' | 'PATROL';
export type MissionStatus = 'PENDING' | 'ASSIGNED' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';

export interface Uav {
  id?: number;
  name: string;
  model: string;
  latitude: number;
  longitude: number;
  batteryLevel: number;
  maxRangeKm: number;
  status: UavStatus;
}

export interface Mission {
  id?: number;
  title: string;
  latitude: number;
  longitude: number;
  priority: MissionPriority;
  type: MissionType;
  status?: MissionStatus;
  requiredRangeKm: number;
  estimatedDurationMinutes?: number;
}

export interface AssignmentResult {
  assignmentId: number;
  uavId: number;
  uavName: string;
  missionId: number;
  missionTitle: string;
  estimatedDistanceKm: number;
  estimatedBatteryUsage: number;
  assignmentScore: number;
  status: string;
  assignedAt: string;
}

export interface DashboardStats {
  totalUavs: number;
  availableUavs: number;
  activeMissions: number;
  completedMissions: number;
  averageBatteryLevel: number;
}
