import { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet';
import L from 'leaflet';
import { uavApi } from '../api/uavApi';
import { missionApi } from '../api/missionApi';
import { assignmentApi } from '../api/assignmentApi';
import type { AssignmentMapEntry, Mission, Uav } from '../types';

// Emoji-based divIcons read more like a live ops board than the default
// Leaflet pin — a drone for UAVs, a target for mission sites.
function emojiIcon(emoji: string, size: number) {
  return new L.DivIcon({
    html: `<div style="font-size:${size}px; line-height:1; filter: drop-shadow(0 2px 3px rgba(0,0,0,.5));">${emoji}</div>`,
    className: 'emoji-marker',
    iconSize: [size, size],
    iconAnchor: [size / 2, size / 2],
  });
}

const uavIcon = emojiIcon('✈️', 28);
const missionIcon = emojiIcon('🎯', 26);

export default function MapPage() {
  const [uavs, setUavs] = useState<Uav[]>([]);
  const [missions, setMissions] = useState<Mission[]>([]);
  const [assignments, setAssignments] = useState<AssignmentMapEntry[]>([]);

  useEffect(() => {
    uavApi.list().then(setUavs).catch(() => {});
    missionApi.list().then(setMissions).catch(() => {});
    assignmentApi.listActive().then(setAssignments).catch(() => {});
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
          {/* UAV -> Mission route lines for active assignments */}
          {assignments.map((a) => (
            <Polyline
              key={`route-${a.assignmentId}`}
              positions={[[a.uavLatitude, a.uavLongitude], [a.missionLatitude, a.missionLongitude]]}
              pathOptions={{ color: '#4dd0e1', weight: 2, dashArray: '6 6', opacity: 0.85 }}
            />
          ))}
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
