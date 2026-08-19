# 🚆 Train Booking Backend

A RESTful train booking backend built with **Java 21 and Spring Boot**. The project provides user authentication, JWT-based authorization, train management, seat-aware booking, booking history, cancellation, and secure DTO-based API responses.

## 🎯 Project Overview

This project simulates the backend of a train reservation system. Users can register and log in, receive a JWT token, access protected APIs, view trains, book seats, view their bookings, and cancel bookings.

The application follows a layered backend architecture:

```text
Client / Postman
       ↓
REST Controller
       ↓
Service Layer
       ↓
Repository Layer
       ↓
MySQL Database
```

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 3.5.16 | Backend framework |
| Spring Web | REST API development |
| Spring Security | Authentication and authorization |
| JWT | Stateless authentication |
| BCrypt | Password hashing |
| Spring Data JPA | Database access |
| Hibernate | ORM / entity mapping |
| MySQL 8 | Relational database |
| Gradle | Build and dependency management |
| Lombok | Boilerplate reduction |
| Postman | API testing |

## 🔐 Authentication & Security

The application uses JWT-based authentication.

```text
Register
   ↓
Password hashed with BCrypt
   ↓
Login
   ↓
JWT generated
   ↓
Bearer Token sent with requests
   ↓
JWT Authentication Filter
   ↓
Spring Security Authentication
   ↓
Protected API access
```

### Security features

- Passwords are stored using BCrypt hashing.
- JWT is used for stateless authentication.
- Protected APIs require a valid Bearer token.
- Authentication endpoints are publicly accessible.
- Sensitive configuration values are supplied through environment variables.
- API responses use DTOs so the stored password hash is not exposed.
- Booking creation identifies the authenticated user from the Spring Security authentication context rather than accepting a user ID from the request.

## ✨ Features

### User Authentication

- User registration
- Email and mobile uniqueness checks
- BCrypt password hashing
- User login
- JWT token generation
- JWT request authentication

### Train Management

- Add trains
- View all trains
- Get train by ID
- Delete trains
- Track total and available seats

### Booking Management

- Create a booking for the authenticated user
- Check train availability before booking
- Assign a seat number
- Decrease available seats after booking
- View user bookings
- View booking by ID
- Cancel a booking
- Restore seat availability after cancellation
- Keep cancelled bookings as booking history

### API Response Security

The application uses response DTOs such as `UserResponse` and `BookingResponse` instead of directly exposing JPA entities. This prevents sensitive fields such as the BCrypt password hash from appearing in API responses.

## 📂 Project Structure

```text
src/main/java/com/shiva/trainbookingbackend
│
├── controller
│   ├── AuthController.java
│   ├── BookingController.java
│   └── TrainController.java
│
├── dto
│   ├── BookingResponse.java
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   ├── RegisterRequest.java
│   └── UserResponse.java
│
├── entity
│   ├── Booking.java
│   ├── Train.java
│   └── User.java
│
├── repository
│   ├── BookingRepository.java
│   ├── TrainRepository.java
│   └── UserRepository.java
│
├── security
│   ├── CustomUserDetailsService.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtService.java
│   └── SecurityConfig.java
│
├── service
│   ├── AuthService.java
│   ├── BookingService.java
│   └── TrainService.java
│
└── TrainBookingBackendApplication.java
```

## 🗄️ Database Design

The project uses three main entities:

```text
users
  │
  │ 1
  │
  │ *
bookings
  │
  │ *
  │
  │ 1
trains
```

### Users

Stores registered user information, including name, email, mobile number, BCrypt password hash, and role.

### Trains

Stores train number, train name, source, destination, total seats, and available seats.

### Bookings

Stores the user, train, passenger information, seat number, booking date, and booking status.

## 🌐 Main API Endpoints

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT |

### Trains

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/trains` | Add a train |
| GET | `/api/trains` | Get all trains |
| GET | `/api/trains/{id}` | Get train by ID |
| DELETE | `/api/trains/{id}` | Delete a train |

### Bookings

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/bookings` | Create booking for authenticated user |
| GET | `/api/bookings/user/{userId}` | Get bookings for a user |
| GET | `/api/bookings/{id}` | Get booking by ID |
| DELETE | `/api/bookings/{id}` | Cancel booking and restore seat |

Protected endpoints require:

```text
Authorization: Bearer <JWT_TOKEN>
```

## ⚙️ Configuration

The application reads sensitive configuration from environment variables rather than storing secrets directly in Git.

Example `application.properties` configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/train_booking_db
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

Set your environment variables before starting the application:

```text
DB_PASSWORD=your_database_password
JWT_SECRET=your_long_random_secret
```

**Never commit real passwords, JWT secrets, API keys, or other credentials to GitHub.**

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone https://github.com/Shivasingh15/train-booking-backend.git
cd train-booking-backend
```

### 2. Create the MySQL database

```sql
CREATE DATABASE train_booking_db;
```

### 3. Configure environment variables

Set `DB_PASSWORD` and `JWT_SECRET` in your local environment.

### 4. Run the application

Windows:

```powershell
.\gradlew.bat bootRun
```

or run `TrainBookingBackendApplication` from IntelliJ IDEA.

The server runs on:

```text
http://localhost:8080
```

## 🧪 API Testing

The APIs were tested using Postman, including:

- User registration
- Login and JWT generation
- Protected API access with Bearer tokens
- Train creation and retrieval
- Booking creation
- Seat availability updates
- Booking retrieval
- Booking cancellation
- Seat restoration
- DTO-based secure responses

## 🧠 Key Backend Concepts Demonstrated

- RESTful API design
- Layered architecture
- Dependency Injection
- JWT authentication
- Spring Security filter chain
- BCrypt password hashing
- JPA entity relationships
- Repository pattern
- DTO pattern
- MySQL persistence
- Transactional business logic concepts
- HTTP methods and status handling
- Environment-based secret management

## 🚀 Future Improvements

Possible future improvements include:

- Role-based authorization for admin/train-management operations
- Search trains by source and destination
- Pagination and sorting
- Global exception handling with structured error responses
- Transaction-safe/concurrency-safe seat allocation
- Refresh tokens
- API documentation with Swagger/OpenAPI
- Automated unit and integration tests

## 👨‍💻 Author

**Shiva Singh**

GitHub: [Shivasingh15](https://github.com/Shivasingh15)

## 📌 Interview Summary

This project demonstrates how a Spring Boot backend can authenticate users with JWT, persist data using JPA/Hibernate and MySQL, protect REST APIs with Spring Security, and implement a basic train reservation workflow with booking, seat availability, and cancellation logic.