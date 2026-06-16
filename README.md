# UAV Fleet Mission Planner

A defense-inspired mission planning and UAV fleet management simulation system built with **Spring Boot**, **React**, **PostgreSQL** and **Leaflet**.

> This project is a pure planning / simulation / route-optimization system. There is **no** weapons, targeting, or attack functionality of any kind — it focuses on fleet management, mission scheduling, and battery/range-aware route assignment.

## Features

- UAV fleet management (CRUD)
- Mission creation and lifecycle tracking
- **Auto mission assignment** — a scoring algorithm picks the best available UAV for a mission
- Battery-aware, range-aware route planning
- Real-time fleet dashboard (totals, availability, battery health, completed missions)
- Map-based visualization of UAVs and missions (Leaflet / OpenStreetMap)
- JWT authentication
- REST API documentation with Swagger / OpenAPI
- Unit tests for the assignment algorithm

## Architecture

```
┌──────────────┐        REST/JSON + JWT        ┌───────────────────┐       JDBC      ┌────────────┐
│ React + Vite │  <-------------------------->  │   Spring Boot API  │ <------------>  │ PostgreSQL │
│ (Leaflet UI) │                                 │  (JPA + Security)  │                 │            │
└──────────────┘                                 └───────────────────┘                 └────────────┘
```

## Tech Stack

| Layer      | Technology                                  |
|------------|----------------------------------------------|
| Frontend   | React, Vite, TypeScript, Leaflet (react-leaflet) |
| Backend    | Spring Boot 3 (Web, Data JPA, Security, Validation) |
| Database   | PostgreSQL 16                                |
| Auth       | JWT (jjwt)                                   |
| Docs       | springdoc-openapi (Swagger UI)               |
| Container  | Docker / docker-compose                      |
| Testing    | JUnit 5, Mockito                             |

## Auto-Assignment Algorithm

When a mission is auto-assigned, every `AVAILABLE` UAV is filtered against hard
eligibility constraints, then scored. The UAV with the **lowest** score wins.

```
score = distanceScore + batteryScore + availabilityScore + priorityScore

distanceScore     = great-circle distance (km) from UAV to mission (Haversine formula)
batteryScore      = 100 - batteryLevel                     (fuller battery -> lower score)
availabilityScore = 0 if AVAILABLE, otherwise a large penalty
priorityScore      = negative offset that grows with mission priority
                      (LOW=0, MEDIUM=-5, HIGH=-12, CRITICAL=-25)
```

Hard eligibility filters (applied before scoring — a UAV failing any of these is excluded):
- UAV status must be `AVAILABLE`.
- UAV's max range must cover the mission's required range **and** the round trip distance.
- Remaining battery after the round trip must keep at least a 15% safety reserve.

This is intentionally a simple, explainable greedy scorer for v1. Planned extensions:
- Dijkstra / A* route planning around no-fly zones
- Multi-UAV mission optimization (assigning several missions at once)
- Battery-aware dynamic re-routing

## Database Schema

```
users          (id, username, password, role)
uavs           (id, name, model, latitude, longitude, batteryLevel, maxRangeKm, status, createdAt)
missions       (id, title, latitude, longitude, priority, type, status, requiredRangeKm, estimatedDurationMinutes, createdAt)
assignments    (id, uavId, missionId, estimatedDistanceKm, estimatedBatteryUsage, assignmentScore, status, assignedAt)
mission_logs   (id, missionId, message, loggedAt)
```

## API Endpoints

| Method | Endpoint                  | Description                          |
|--------|----------------------------|---------------------------------------|
| POST   | `/api/auth/register`       | Create a new operator account         |
| POST   | `/api/auth/login`          | Log in and receive a JWT              |
| GET    | `/api/uavs`                | List all UAVs                         |
| POST   | `/api/uavs`                | Add a new UAV                         |
| GET    | `/api/uavs/{id}`           | Get a single UAV                      |
| PUT    | `/api/uavs/{id}`           | Update a UAV                          |
| DELETE | `/api/uavs/{id}`           | Remove a UAV                          |
| GET    | `/api/missions`            | List all missions                     |
| POST   | `/api/missions`            | Create a new mission                  |
| GET    | `/api/missions/{id}`       | Get a single mission                  |
| PUT    | `/api/missions/{id}`       | Update a mission                      |
| DELETE | `/api/missions/{id}`       | Remove a mission                      |
| POST   | `/api/missions/{id}/assign`| Run auto-assignment for this mission  |
| GET    | `/api/dashboard/stats`     | Aggregated fleet statistics           |

Full interactive docs: `http://localhost:8080/swagger-ui.html`

## Getting Started

### Option A — Docker Compose (recommended)

```bash
docker compose up --build
```

This starts PostgreSQL + the Spring Boot backend. Then run the frontend separately:

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`. A demo admin user (`admin` / `admin123`) and a few
sample UAVs/missions are seeded automatically on first boot.

### Option B — Run locally

**Backend**
```bash
cd backend
# Configure DB_HOST/DB_USER/DB_PASSWORD env vars or edit src/main/resources/application.yml
mvn spring-boot:run
```

**Frontend**
```bash
cd frontend
npm install
npm run dev
```

### Running Tests

```bash
cd backend
mvn test
```

## Roadmap

- [ ] Dijkstra / A* route planning
- [ ] Greedy multi-mission batch assignment
- [ ] Multi-UAV optimization (assignment as a min-cost matching problem)
- [ ] No-fly zone avoidance
- [ ] Battery-aware dynamic re-routing mid-mission

## License

MIT
