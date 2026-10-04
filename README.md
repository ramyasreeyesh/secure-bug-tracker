# 🐞 Secure Bug Tracker

A full-stack bug/task tracking system built with Spring Boot, featuring JWT authentication, role-based access control, and a complete CI/CD pipeline.

## Features

- **JWT Authentication** — secure register/login with BCrypt password hashing and refresh token support
- **Role-Based Access Control** — Admin, Developer, and Reporter roles with different permissions (e.g., only Admins can delete bugs)
- **Bug Lifecycle Management** — track bugs through Open → In Progress → Tested → Closed
- **RESTful API** — full CRUD operations with pagination and status filtering
- **Entity Relationships** — bugs linked to the reporting user via JPA `@ManyToOne` relationships
- **Service Layer Architecture** — clean separation between Controller, Service, and Repository layers
- **Global Exception Handling** — consistent, structured JSON error responses
- **API Documentation** — interactive Swagger/OpenAPI docs
- **Automated Testing** — JUnit + Mockito unit tests
- **CI/CD Pipeline** — GitHub Actions automatically runs tests on every push
- **Simple Frontend** — vanilla HTML/JS interface to interact with the API without Postman

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 4, Spring Security, Spring Data JPA |
| Database | MySQL |
| Authentication | JWT (jjwt), BCrypt |
| API Docs | Springdoc OpenAPI / Swagger UI |
| Testing | JUnit 5, Mockito |
| CI/CD | GitHub Actions |
| Frontend | HTML, CSS, vanilla JavaScript |
| Build Tool | Maven |

## Getting Started

### Prerequisites
- Java 17+
- MySQL running locally
- Maven (or use the included `mvnw` wrapper)

### Setup

1. Clone the repo:
git clone https://github.com/ramyasreeyesh/secure-bug-tracker.git
cd secure-bug-tracker

2. Create a MySQL database:
```sql
   CREATE DATABASE bugtracker;
```

3. Set your database credentials as environment variables (or edit `application.properties` directly for local testing):
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_custom_secret_key

4. Run the app:
./mvnw spring-boot:run

5. Open your browser:
http://localhost:8080

### Running Tests

./mvnw test


### API Documentation
Once running, visit:

http://localhost:8080/swagger-ui/index.html


## API Endpoints

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | `/api/auth/register` | Register a new user | No |
| POST | `/api/auth/login` | Login, returns JWT + refresh token | No |
| POST | `/api/auth/refresh` | Get a new access token | No (refresh token) |
| GET | `/api/bugs` | List bugs (paginated, filterable by status) | Yes |
| GET | `/api/bugs/{id}` | Get a single bug | Yes |
| POST | `/api/bugs` | Create a new bug | Yes |
| PUT | `/api/bugs/{id}` | Update a bug | Yes |
| DELETE | `/api/bugs/{id}` | Delete a bug | Yes (Admin only) |

## Architecture
Controller → Service → Repository → Database


- **Controllers** handle HTTP requests/responses only
- **Services** contain business logic
- **Repositories** handle data access via Spring Data JPA
- **JWT Filter** intercepts requests to validate tokens before reaching controllers

## Author

Ramyasree Y