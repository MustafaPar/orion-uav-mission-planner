import { useEffect, useState } from 'react';
import { missionApi } from '../api/missionApi';
import type { AssignmentResult, Mission, MissionPriority, MissionType } from '../types';

const emptyMission: Mission = {
  title: '',
  latitude: 41.0,
  longitude: 29.0,
  priority: 'MEDIUM',
  type: 'SURVEILLANCE',
  requiredRangeKm: 10,
  estimatedDurationMinutes: 30,
};

export default function MissionPage() {
  const [missions, setMissions] = useState<Mission[]>([]);
  const [form, setForm] = useState<Mission>(emptyMission);
  const [error, setError] = useState<string | null>(null);
  const [result, setResult] = useState<AssignmentResult | null>(null);

  const load = () => missionApi.list().then(setMissions).catch(() => setError('Failed to load missions.'));

  useEffect(() => {
    load();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await missionApi.create(form);
      setForm(emptyMission);
      load();
    } catch {
      setError('Failed to create mission. Check the form values.');
    }
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    await missionApi.remove(id);
    load();
  };

  const handleAssign = async (id?: number) => {
    if (!id) return;
    setError(null);
    setResult(null);
    try {
      const assigned = await missionApi.assign(id);
      setResult(assigned);
      load();
    } catch (err: any) {
      setError(err?.response?.data?.message ?? 'No eligible UAV could be assigned to this mission.');
    }
  };

  return (
    <div>
      <h2>Missions</h2>
      {error && <div className="error-banner">{error}</div>}

      {result && (
        <div className="assignment-card">
          <div className="assignment-card-header">
            <span className="assignment-card-icon">🛰️</span>
            <div>
              <div className="assignment-card-title">Assigned to {result.uavName}</div>
              <div className="muted small">for mission "{result.missionTitle}"</div>
            </div>
            <button className="secondary assignment-card-close" onClick={() => setResult(null)}>✕</button>
          </div>
          <div className="assignment-card-reason-label">Why this UAV?</div>
          <ul className="assignment-card-reasons">
            {result.reasons.map((reason, i) => (
              <li key={i}>{reason}</li>
            ))}
          </ul>
        </div>
      )}

      <form className="form-grid" onSubmit={handleSubmit}>
        <label>
          Title
          <input value={form.title} onChange={(e) => setForm({ ...form, title: e.target.value })} required />
        </label>
        <label>
          Latitude
          <input type="number" step="0.0001" value={form.latitude}
                 onChange={(e) => setForm({ ...form, latitude: parseFloat(e.target.value) })} required />
        </label>
        <label>
          Longitude
          <input type="number" step="0.0001" value={form.longitude}
                 onChange={(e) => setForm({ ...form, longitude: parseFloat(e.target.value) })} required />
        </label>
        <label>
          Priority
          <select value={form.priority} onChange={(e) => setForm({ ...form, priority: e.target.value as MissionPriority })}>
            <option value="LOW">Low</option>
            <option value="MEDIUM">Medium</option>
            <option value="HIGH">High</option>
            <option value="CRITICAL">Critical</option>
          </select>
        </label>
        <label>
          Type
          <select value={form.type} onChange={(e) => setForm({ ...form, type: e.target.value as MissionType })}>
            <option value="SURVEILLANCE">Surveillance</option>
            <option value="MAPPING">Mapping</option>
            <option value="DELIVERY">Delivery</option>
            <option value="PATROL">Patrol</option>
          </select>
        </label>
        <label>
          Required Range (km)
          <input type="number" min={1} value={form.requiredRangeKm}
                 onChange={(e) => setForm({ ...form, requiredRangeKm: parseFloat(e.target.value) })} required />
        </label>
        <label>
          Est. Duration (min)
          <input type="number" min={1} value={form.estimatedDurationMinutes}
                 onChange={(e) => setForm({ ...form, estimatedDurationMinutes: parseInt(e.target.value, 10) })} />
        </label>
        <div style={{ alignSelf: 'end' }}>
          <button type="submit">Add Mission</button>
        </div>
      </form>

      <table>
        <thead>
          <tr>
            <th>Title</th><th>Type</th><th>Priority</th><th>Status</th><th></th>
          </tr>
        </thead>
        <tbody>
          {missions.map((m) => (
            <tr key={m.id}>
              <td>{m.title}</td>
              <td>{m.type}</td>
              <td><span className={`badge ${m.priority.toLowerCase()}`}>{m.priority}</span></td>
              <td>{m.status}</td>
              <td>
                <button onClick={() => handleAssign(m.id)} disabled={m.status !== 'PENDING'}>
                  Auto-Assign
                </button>{' '}
                <button className="secondary" onClick={() => handleDelete(m.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
