import { NavLink, Route, Routes } from 'react-router-dom';
import DashboardPage from './pages/DashboardPage';
import UavPage from './pages/UavPage';
import MissionPage from './pages/MissionPage';
import MapPage from './pages/MapPage';

export default function App() {
  return (
    <div className="app-shell">
      <header className="topbar">
        <h1>🛰️ Orion UAV Mission Planner</h1>
        <nav>
          <NavLink to="/" end>Dashboard</NavLink>
          <NavLink to="/uavs">UAVs</NavLink>
          <NavLink to="/missions">Missions</NavLink>
          <NavLink to="/map">Map</NavLink>
        </nav>
      </header>

      <main className="content">
        <Routes>
          <Route path="/" element={<DashboardPage />} />
          <Route path="/uavs" element={<UavPage />} />
          <Route path="/missions" element={<MissionPage />} />
          <Route path="/map" element={<MapPage />} />
        </Routes>
      </main>
    </div>
  );
}
