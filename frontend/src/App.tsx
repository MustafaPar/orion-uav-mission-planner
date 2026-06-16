import { useEffect, useState } from 'react';
import { NavLink, Route, Routes } from 'react-router-dom';
import DashboardPage from './pages/DashboardPage';
import UavPage from './pages/UavPage';
import MissionPage from './pages/MissionPage';
import MapPage from './pages/MapPage';
import LoginPage from './pages/LoginPage';

export default function App() {
  const [username, setUsername] = useState<string | null>(() => localStorage.getItem('uav_username'));

  // If the backend ever rejects the stored token (expired/invalid), the
  // apiClient interceptor below clears it and we fall back to the login screen.
  useEffect(() => {
    const handler = () => setUsername(null);
    window.addEventListener('uav_auth_expired', handler);
    return () => window.removeEventListener('uav_auth_expired', handler);
  }, []);

  if (!username) {
    return <LoginPage onLogin={setUsername} />;
  }

  const handleLogout = () => {
    localStorage.removeItem('uav_token');
    localStorage.removeItem('uav_username');
    setUsername(null);
  };

  return (
    <div className="app-shell">
      <header className="topbar">
        <a href="/" className="brand">
          <img src="/brand/logo.svg" alt="Orion UAV Mission Planner" className="brand-logo" />
        </a>
        <nav>
          <NavLink to="/" end>Dashboard</NavLink>
          <NavLink to="/uavs">UAVs</NavLink>
          <NavLink to="/missions">Missions</NavLink>
          <NavLink to="/map">Map</NavLink>
        </nav>
        <div className="topbar-right">
          <span className="muted">{username}</span>
          <button className="secondary" onClick={handleLogout}>Sign out</button>
        </div>
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
