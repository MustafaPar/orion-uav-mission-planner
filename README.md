# Orion UAV Mission Planner

A full-stack UAV fleet management and mission planning simulation platform built with **Spring Boot**, **React**, **PostgreSQL**, and **Leaflet**.

> Orion is a defense-inspired mission planning system designed to simulate UAV fleet operations, mission assignment workflows, route visualization, and operational monitoring.
>
> This project is strictly a planning and simulation platform and contains no weapons, targeting, or attack functionality.

---

## Screenshots

### Authentication

![Login](docs/screenshots/01-login.png)

Secure authentication system with protected application routes.

### Operations Dashboard

![Dashboard](docs/screenshots/02-dashboard.png)

Real-time fleet overview including:

- UAV availability tracking
- Active mission monitoring
- Battery health statistics
- Mission completion metrics
- Fleet status summaries

### UAV Fleet Management

![UAV Fleet](docs/screenshots/03-uavs.png)

Manage UAV assets with:

- Fleet registration
- Status management
- Battery monitoring
- Range tracking
- Location management

### Mission Planning & Assignment

![Mission Management](docs/screenshots/04-missions.png)

Mission lifecycle management including:

- Mission creation
- Priority classification
- Mission categorization
- Automatic UAV assignment
- Assignment status tracking

### Interactive Mission Map

![Mission Map](docs/screenshots/05-map.png)

Leaflet-powered mission visualization featuring:

- UAV positioning
- Mission target locations
- Route visualization
- Geographic mission overview

---

## Features

### Fleet Management

- Create, update, and remove UAV assets
- Track UAV status and availability
- Monitor battery levels and operational range
- Manage geographic positioning

### Mission Management

- Create surveillance, patrol, and mapping missions
- Define mission priorities
- Track mission status
- Assign UAVs manually or automatically

### Smart Auto Assignment

Orion includes a mission assignment algorithm that evaluates available UAVs and selects the most suitable asset based on operational constraints.

Assignment scoring considers:

- UAV availability
- Battery level
- Operational range
- Mission requirements
- Current assignment status

### Dashboard Analytics

- Fleet utilization overview
- Battery distribution monitoring
- Mission completion tracking
- Active asset visualization

### Interactive Mapping

- Leaflet-based visualization
- Live-style UAV positioning
- Mission target display
- Assignment route rendering

---

## Technology Stack

### Backend

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- REST API

### Frontend

- React
- JavaScript
- Axios
- Leaflet
- CSS

### Database

- PostgreSQL

### DevOps & Tools

- Docker
- Docker Compose
- Git
- GitHub
- Postman

---

## System Architecture

```text
React Frontend
        │
        ▼
 Spring Boot REST API
        │
        ▼
 PostgreSQL Database
```

---

## Project Structure

```text
orion-uav-mission-planner/
│
├── backend/
│   ├── controllers/
│   ├── services/
│   ├── repositories/
│   ├── entities/
│   └── security/
│
├── frontend/
│   ├── components/
│   ├── pages/
│   ├── services/
│   └── assets/
│
└── docs/
    └── screenshots/
```

---

## Getting Started

### Prerequisites

- Java 17+
- Node.js 20+
- PostgreSQL
- Docker

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Docker

```bash
docker compose up --build
```

---

## Learning Objectives

This project was developed to demonstrate:

- Full-stack application development
- REST API architecture
- Spring Security integration
- Database design with PostgreSQL
- Interactive geospatial interfaces
- Mission assignment algorithms
- Modern React frontend development

---

## Disclaimer

Orion is a software simulation and mission planning project created for educational and portfolio purposes.

It does not control real UAVs and contains no weapons, targeting, surveillance, or attack capabilities.

---

## Author

**Mustafa Par**

Computer Engineering Student

GitHub: https://github.com/MustafaPar
