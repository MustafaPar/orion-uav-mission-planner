import { useEffect, useState } from 'react';
import { uavApi } from '../api/uavApi';
import type { Uav, UavStatus } from '../types';

const emptyUav: Uav = {
  name: '',
  model: '',
  latitude: 41.0,
  longitude: 29.0,
  batteryLevel: 100,
  maxRangeKm: 50,
  status: 'AVAILABLE',
};

export default function UavPage() {
  const [uavs, setUavs] = useState<Uav[]>([]);
  const [form, setForm] = useState<Uav>(emptyUav);
  const [error, setError] = useState<string | null>(null);

  const load = () => uavApi.list().then(setUavs).catch(() => setError('Failed to load UAVs.'));

  useEffect(() => {
    load();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await uavApi.create(form);
      setForm(emptyUav);
      load();
    } catch {
      setError('Failed to create UAV. Check the form values.');
    }
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    await uavApi.remove(id);
    load();
  };

  return (
    <div>
      <h2>UAV Fleet</h2>
      {error && <div className="error-banner">{error}</div>}

      <form className="form-grid" onSubmit={handleSubmit}>
        <label>
          Name
          <input value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required />
        </label>
        <label>
          Model
          <input value={form.model} onChange={(e) => setForm({ ...form, model: e.target.value })} required />
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
          Battery (%)
          <input type="number" min={0} max={100} value={form.batteryLevel}
                 onChange={(e) => setForm({ ...form, batteryLevel: parseFloat(e.target.value) })} required />
        </label>
        <label>
          Max Range (km)
          <input type="number" min={1} value={form.maxRangeKm}
                 onChange={(e) => setForm({ ...form, maxRangeKm: parseFloat(e.target.value) })} required />
        </label>
        <label>
          Status
          <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value as UavStatus })}>
            <option value="AVAILABLE">Available</option>
            <option value="ASSIGNED">Assigned</option>
            <option value="MAINTENANCE">Maintenance</option>
          </select>
        </label>
        <div style={{ alignSelf: 'end' }}>
          <button type="submit">Add UAV</button>
        </div>
      </form>

      <table>
        <thead>
          <tr>
            <th>Name</th><th>Model</th><th>Battery</th><th>Range (km)</th><th>Status</th><th></th>
          </tr>
        </thead>
        <tbody>
          {uavs.map((u) => (
            <tr key={u.id}>
              <td>{u.name}</td>
              <td>{u.model}</td>
              <td>{u.batteryLevel}%</td>
              <td>{u.maxRangeKm}</td>
              <td><span className={`badge ${u.status.toLowerCase()}`}>{u.status}</span></td>
              <td><button className="secondary" onClick={() => handleDelete(u.id)}>Delete</button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
