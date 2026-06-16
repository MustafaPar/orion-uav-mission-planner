import { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';
import { uavApi } from '../api/uavApi';
import { missionApi } from '../api/missionApi';
import type { Mission, Uav } from '../types';

const uavIcon = new L.Icon({
  iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
  shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
});

const missionIcon = new L.Icon({
  iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
  shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  className: 'mission-marker',
});

export default function MapPage() {
  const [uavs, setUavs] = useState<Uav[]>([]);
  const [missions, setMissions] = useState<Mission[]>([]);

  useEffect(() => {
    uavApi.list().then(setUavs).catch(() => {});
    missionApi.list().then(setMissions).catch(() => {});
  }, []);

  return (
    <div>
      <h2>Mission Map</h2>
      <div className="map-container">
        <MapContainer center={[41.01, 28.97]} zoom={11} style={{ height: '100%', width: '100%' }}>
          <TileLayer
            attribution='&copy; OpenStreetMap contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          {uavs.map((u) => (
            <Marker key={`uav-${u.id}`} position={[u.latitude, u.longitude]} icon={uavIcon}>
              <Popup>
                <b>{u.name}</b> ({u.model})<br />
                Battery: {u.batteryLevel}% — Status: {u.status}
              </Popup>
            </Marker>
          ))}
          {missions.map((m) => (
            <Marker key={`mission-${m.id}`} position={[m.latitude, m.longitude]} icon={missionIcon}>
              <Popup>
                <b>{m.title}</b><br />
                Type: {m.type} — Priority: {m.priority}<br />
                Status: {m.status}
              </Popup>
            </Marker>
          ))}
        </MapContainer>
      </div>
    </div>
  );
}
