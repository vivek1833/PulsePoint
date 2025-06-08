# PulsePoint Frontend

A modern React-based hospital management system frontend.

## Prerequisites

- Node.js (v14 or higher)
- npm (v6 or higher)

## Getting Started

1. Clone the repository
2. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
3. Install dependencies:
   ```bash
   npm install
   ```
4. Create a `.env` file based on `.env.example`:
   ```bash
   cp .env.example .env
   ```
5. Update the environment variables in `.env` as needed
6. Start the development server:
   ```bash
   npm start
   ```

The application will be available at `http://localhost:3000`

## Environment Variables

- `REACT_APP_API_URL`: Backend API URL (default: http://localhost:8080/api)

## Available Scripts

- `npm start`: Runs the app in development mode
- `npm test`: Launches the test runner
- `npm run build`: Builds the app for production
- `npm run eject`: Ejects from Create React App

## Project Structure

frontend/
├── public/
├── src/
│ ├── components/
│ │ ├── common/
│ │ └── ...
│ ├── pages/
│ ├── services/
│ ├── styles/
│ ├── utils/
│ └── App.js
├── .env
├── .env.example
└── package.json

## Features

- Patient Management
- Sorting and Pagination
- Responsive Design
- Modern UI/UX