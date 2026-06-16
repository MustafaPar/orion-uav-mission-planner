import { useEffect, useState } from 'react';
import { dashboardApi } from '../api/dashboardApi';
import type { DashboardStats } from '../types';

export default function DashboardPage() {
  const [stats, setStats] = useState<DashboardStats | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    dashboardApi
      .stats()
      .then(setStats)
      .catch(() => setError('Could not load dashboard stats. Is the backend running?'));
  }, []);

  return (
    <div>
      <h2>Fleet Overview</h2>
      {error && <div className="error-banner">{error}</div>}
      {stats && (
        <div className="stats-grid">
          <div className="stat-card">
            <div className="label">Total UAVs</div>
            <div className="value">{stats.totalUavs}</div>
          </div>
          <div className="stat-card">
            <div className="label">Available UAVs</div>
            <div className="value">{stats.availableUavs}</div>
          </div>
          <div className="stat-card">
            <div className="label">Active Missions</div>
            <div className="value">{stats.activeMissions}</div>
          </div>
          <div className="stat-card">
            <div className="label">Completed Missions</div>
            <div className="value">{stats.completedMissions}</div>
          </div>
          <div className="stat-card">
            <div className="label">Avg. Battery Level</div>
            <div className="value">{stats.averageBatteryLevel}%</div>
          </div>
        </div>
      )}
    </div>
  );
}
