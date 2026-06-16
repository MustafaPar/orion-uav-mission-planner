import { useEffect, useState } from 'react';
import { dashboardApi } from '../api/dashboardApi';
import { uavApi } from '../api/uavApi';
import { missionApi } from '../api/missionApi';
import type { DashboardStats, Mission, Uav } from '../types';

export default function DashboardPage() {
  const [stats, setStats] = useState<DashboardStats | null>(null);
  const [uavs, setUavs] = useState<Uav[]>([]);
  const [missions, setMissions] = useState<Mission[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    Promise.all([dashboardApi.stats(), uavApi.list(), missionApi.list()])
      .then(([s, u, m]) => {
        setStats(s);
        setUavs(u);
        setMissions(m);
      })
      .catch(() => setError('Could not load dashboard stats. Is the backend running?'));
  }, []);

  const completionRate = missions.length > 0
    ? Math.round((missions.filter((m) => m.status === 'COMPLETED').length / missions.length) * 100)
    : 0;

  const recentMissions = [...missions]
    .sort((a, b) => (b.id ?? 0) - (a.id ?? 0))
    .slice(0, 5);

  const batteryColor = (level: number) => (level > 60 ? 'var(--green)' : level > 30 ? 'var(--yellow)' : 'var(--red)');

  return (
    <div>
      <h2 className="page-title">
        <img src="/brand/favicon.svg" alt="" className="page-title-mark" />
        Fleet Overview
      </h2>
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

      <div className="dashboard-grid">
        <div className="panel">
          <h3>Mission Completion Rate</h3>
          <div className="completion-bar-track">
            <div className="completion-bar-fill" style={{ width: `${completionRate}%` }} />
          </div>
          <div className="muted small" style={{ marginTop: 8 }}>
            {missions.filter((m) => m.status === 'COMPLETED').length} of {missions.length} missions completed ({completionRate}%)
          </div>
        </div>

        <div className="panel">
          <h3>Battery Distribution</h3>
          {uavs.length === 0 && <div className="muted small">No UAVs in the fleet yet.</div>}
          {uavs.map((u) => (
            <div key={u.id} className="battery-row">
              <span className="battery-row-name">{u.name}</span>
              <div className="battery-bar-track">
                <div
                  className="battery-bar-fill"
                  style={{ width: `${u.batteryLevel}%`, background: batteryColor(u.batteryLevel) }}
                />
              </div>
              <span className="battery-row-pct">{u.batteryLevel}%</span>
            </div>
          ))}
        </div>

        <div className="panel">
          <h3>Active UAV Fleet</h3>
          <table>
            <thead>
              <tr><th>Name</th><th>Model</th><th>Status</th></tr>
            </thead>
            <tbody>
              {uavs.map((u) => (
                <tr key={u.id}>
                  <td>{u.name}</td>
                  <td>{u.model}</td>
                  <td><span className={`badge ${u.status.toLowerCase()}`}>{u.status}</span></td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="panel">
          <h3>Recent Missions</h3>
          <table>
            <thead>
              <tr><th>Title</th><th>Priority</th><th>Status</th></tr>
            </thead>
            <tbody>
              {recentMissions.map((m) => (
                <tr key={m.id}>
                  <td>{m.title}</td>
                  <td><span className={`badge ${m.priority.toLowerCase()}`}>{m.priority}</span></td>
                  <td>{m.status}</td>
                </tr>
              ))}
              {recentMissions.length === 0 && (
                <tr><td colSpan={3} className="muted small">No missions yet.</td></tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
