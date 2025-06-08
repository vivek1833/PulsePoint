# PulsePoint Backend

Spring Boot-based hospital management system backend.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

## Getting Started

1. Clone the repository
2. Navigate to the backend directory:
   ```bash
   cd backend
   ```
3. Configure database settings in `application.properties`
4. Build the project:
   ```bash
   mvn clean install
   ```
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The server will start at `http://localhost:8080`

## API Documentation

### Patient Endpoints

#### Get All Patients
GET /api/patient

Query Parameters:
- `pageNumber` (optional, default: 0): Page number
- `pageSize` (optional, default: 10): Items per page
- `sortColumn` (optional, default: created_at): Column to sort by
- `sortDirection` (optional, default: asc): Sort direction (asc/desc)

Response:
```json
[
  {
    "id": "UUID",
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "age": "number",
    "gender": "string",
    "admissionDate": "date",
    "condition": "string",
    "roomNo": "string",
    "contactNo": "string",
    "diagnosis": "string"
  }
]
```

#### Get Patient by ID
GET /api/patient/{id}

Response: Single patient object

#### Create Patient
POST /api/patient

Request Body: Patient object without ID
Response: Created patient object

#### Update Patient
PUT /api/patient/{id}

Request Body: Updated patient object
Response: Updated patient object

#### Delete Patient
DELETE /api/patient/{id}

Response: 204 No Content

## Database Schema

### Users Table
- id (UUID)
- firstName (VARCHAR)
- lastName (VARCHAR)
- email (VARCHAR)
- username (VARCHAR)
- password (VARCHAR)
- type (VARCHAR)
- active (BOOLEAN)
- createdAt (TIMESTAMP)
- updatedAt (TIMESTAMP)

### Patient Table
- id (UUID)
- userId (UUID, FK)
- age (INTEGER)
- gender (VARCHAR)
- admissionDate (DATE)
- condition (VARCHAR)
- roomNo (VARCHAR)
- contactNo (VARCHAR)
- diagnosis (TEXT)
- active (BOOLEAN)
- createdAt (TIMESTAMP)
- updatedAt (TIMESTAMP)

## Security

The API is secured using JWT authentication. Include the JWT token in the Authorization header:
Authorization: Bearer <token>

```

To implement these changes:

1. Create the environment files (.env and .env.example) in the frontend directory
2. Create the global.css file in frontend/src/styles/
3. Update the Loading component
4. Create both README files
5. Update your imports to use the global.css instead of individual CSS files
6. Add .env to your .gitignore file