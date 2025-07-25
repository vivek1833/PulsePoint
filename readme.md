# PulsePoint

## Overview

PulsePoint is a modern web application built with Spring Boot and React. It provides a secure and scalable platform with RESTful API architecture.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Project Structure](#project-structure)
- [Frontend](#frontend)
- [Backend](#backend)
- [API Documentation](#api-documentation)
- [Environment Variables](#environment-variables)
- [Dockerization](#dockerization)
- [Contributing](#contributing)
- [License](#license)

## Features

- User authentication and authorization with JWT
- RESTful API endpoints with Spring Data REST
- Secure email service integration
- PostgreSQL database integration
- Redis integration
- Responsive web interface
- Protected routes and API endpoints

## Tech Stack

### Frontend

- React.js (v19.1.0)
- React Router DOM (v7.6.2)
- Axios for API integration
- Testing utilities (Jest, React Testing Library)

### Backend

- Spring Boot (v3.4.5)
- Java 21
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication
- Spring Mail
- Redis
- Kafka
- Lombok
- Maven

## Getting Started

### Prerequisites

- Java 21 or higher
- Node.js (v16.x or higher)
- npm (v8.x or higher)
- PostgreSQL (v13 or higher)
- Maven
- Docker & Docker Compose

### Installation

1. Clone the repository

```bash
git clone https://github.com/yourusername/pulsepoint.git
cd pulsepoint
```

2. Backend Setup

```bash
cd backend
mvn clean install
```

3. Frontend Setup

```bash
cd frontend
npm install
```

4. Set up environment variables

Backend (`application.properties`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pulsepoint
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
jwt.secret=your_jwt_secret
spring.mail.host=smtp.your-email-provider.com
spring.mail.port=587
spring.mail.username=your_email
spring.mail.password=your_email_password
```

Frontend (`.env`):

```
REACT_APP_API_URL=http://localhost:8080
```

5. Start the applications

Backend:

```bash
cd backend
mvn spring-boot:run
```

Frontend:

```bash
cd frontend
npm start
```

## Project Structure

```
pulsepoint/
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── styles/
│   │   ├── utils/
│   │   ├── App.js
│   │   └── index.js
│   ├── package.json
│   └── README.md
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│   └── README.md
└── README.md
```

## Frontend

The frontend is a React-based single-page application with:

- Component-based architecture
- React Router for navigation
- Axios for API integration
- Jest and React Testing Library for testing

## Backend

The backend is built with Spring Boot and provides:

- RESTful API endpoints
- JWT-based authentication
- PostgreSQL database integration
- Email service integration
- Redis integration
- Kafka consumers
- Spring Security for endpoint protection
- JPA for database operations

## Environment Variables

### Backend (application.properties)

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=
jwt.secret=
spring.mail.host=
spring.mail.port=
spring.mail.username=
spring.mail.password=
```

### Frontend (.env)

```
REACT_APP_API_URL=
```

## Dockerization

This project is fully dockerized. You can run the entire stack (frontend, backend, PostgreSQL, Redis) using Docker Compose.

### Dockerfile Notes

- **Frontend Dockerfile** should use `EXPOSE 80` (not 8090). Nginx inside the container listens on port 80 by default.
- **docker-compose.yml** should map frontend service as `8090:80` so you access the UI at [http://localhost:8090](http://localhost:8090).
- **Backend** is mapped as `8080:8080`.
- **PostgreSQL** is mapped as `5433:5432` (host:container).
- **Redis** is mapped as `6379:6379`.

### How to Run

1. Make sure Docker and Docker Compose are installed.
2. From the project root, run:
   ```bash
   docker-compose up --build
   ```
3. Access the services:
   - **Frontend (UI):** [http://localhost:8090](http://localhost:8090)
   - **Backend (API):** [http://localhost:8080](http://localhost:8080)
   - **PostgreSQL:** Host port 5433 (for database tools)
   - **Redis:** Host port 6379
