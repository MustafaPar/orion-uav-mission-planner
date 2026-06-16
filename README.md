# Orion UAV Mission Planner

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F)
![React](https://img.shields.io/badge/React-19-61DAFB)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1)
![Docker](https://img.shields.io/badge/Docker-2496ED)
![Leaflet](https://img.shields.io/badge/Leaflet-199900)
![License](https://img.shields.io/badge/Portfolio_Project-blue)

A full-stack UAV fleet management and mission planning simulation platform built with **Spring Boot**, **React**, **PostgreSQL**, and **Leaflet**.

> Orion is a defense-inspired mission planning system designed to simulate UAV fleet operations, mission assignment workflows, route visualization, and operational monitoring.
>
> This project is strictly a planning and simulation platform and contains no weapons, targeting, or attack functionality.

---

## Mission Operations Overview

![Mission Map](docs/screenshots/03-map.png)

Orion provides an interactive operational view where UAV assets, mission targets, and assignment routes can be visualized on a geographic map.

---

## Demo

> Demo GIF coming soon

The planned demonstration showcases:

* Creating a mission
* Automatic UAV assignment
* Route visualization
* Mission status updates
* Dashboard analytics

---

## Screenshots

### Operations Dashboard

![Dashboard](docs/screenshots/02-dashboard.png)

### UAV Fleet Management

![UAV Fleet](docs/screenshots/05-uavs.png)

### Mission Planning & Assignment

![Mission Management](docs/screenshots/04-missions-assign.png)

### Authentication

![Login](docs/screenshots/01-login.png)

---

## Features

### Fleet Management

* Create, update, and remove UAV assets
* Track UAV status and availability
* Monitor battery levels and operational range
* Manage geographic positioning

### Mission Management

* Create surveillance, patrol, and mapping missions
* Define mission priorities
* Track mission status
* Assign UAVs manually or automatically

### Smart Auto Assignment

Orion includes a mission assignment algorithm that evaluates available UAVs and selects the most suitable UAV based on operational constraints.

Assignment scoring considers:

* UAV availability
* Battery level
* Operational range
* Distance to mission
* Current assignment status

### Dashboard Analytics

* Fleet utilization overview
* Battery monitoring
* Mission completion tracking
* Operational status summaries

### Interactive Mapping

* Leaflet-powered map visualization
* UAV positioning
* Mission target display
* Route rendering

---

## Technology Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* REST API

### Frontend

* React
* TypeScript
* Axios
* Leaflet
* CSS

### Database

* PostgreSQL

### DevOps & Tools

* Docker
* Docker Compose
* Git
* GitHub
* Postman

---

## Architecture

```text
┌──────────────────────────────┐
│       React Frontend         │
│  Dashboard • Map • Missions  │
└──────────────┬───────────────┘
               │ REST API
               ▼
┌──────────────────────────────┐
│     Spring Boot Backend      │
│ Business Logic • Security    │
│ Assignment Engine • JPA      │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│      PostgreSQL Database     │
│ UAVs • Missions • Users      │
└──────────────────────────────┘
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
│   ├── pages/
│   ├── components/
│   ├── services/
│   └── assets/
│
└── docs/
    └── screenshots/
```

---

## Learning Objectives

This project was developed to demonstrate:

* Full-stack application development
* REST API architecture
* Spring Security integration
* Database design with PostgreSQL
* Interactive geospatial interfaces
* Mission assignment algorithms
* Modern React frontend development

---

## Disclaimer

Orion is a software simulation and mission planning project created for educational and portfolio purposes.

It does not control real UAVs and contains no weapons, targeting, surveillance, or attack capabilities.

---

## Author

**Mustafa Par**

Computer Engineering Student

GitHub: https://github.com/MustafaPar


