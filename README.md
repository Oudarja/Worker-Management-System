## Worker Management System – Spring Boot & ReactJS

A full-stack **Worker Management System** built with **Spring Boot** (backend) and **ReactJS** (frontend).The project provides user authentication (JWT-based), registration, login, and profile management functionalities with a responsive React-based interface.
---

## Prerequisites

- [Java 17+](https://adoptium.net/) (for Spring Boot backend)  
- [Maven](https://maven.apache.org/) (for dependency management)  
- [Node.js & npm](https://nodejs.org/) (for React frontend)  
- [MySQL](https://dev.mysql.com/downloads/) (as the database)  

---

## Architecture
<img width="900" height="500" alt="image" src="https://github.com/user-attachments/assets/84712528-dfbf-4cdf-91cd-93334c45e2ae" />


##  Running the Project with Docker (Future extension)
Can be containerized the application using Docker Compose.
```bash
# Build all services
docker compose build

# Start all services
docker compose up

# Start in detached mode
docker compose up -d

# Stop all services
docker compose down
```
## Accessing the App
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080

## Development Setup (Without Docker)
-Navigate to backend folder 
``cd UserManagementSystem``
-Build the project
``mvn clean install``
-Run the application
``mvn spring-boot:run``
-Navigate to frontend folder
``cd users-management-fronted``
-Install dependencies
``npm install``
-Start the frontend
``npm start``
## Project Structure
### Backend (UserManagementSystem/)
```
src/main/java/com/example/UserManagementSystem
│── config/                 # Security and JWT configuration
│   ├── CorsConfig.java
│   ├── JWTAuthFilter.java
│   ├── SecurityConfig.java
│
│── controller/             # REST controllers
│   └── UserManagementController.java
│
│── dto/                    # Data Transfer Objects
│   └── ReqRes.java
│
│── entity/                 # Entity classes
│   └── OurUsers.java
│
│── repository/             # JPA repositories
│   └── UsersRepo.java
│
│── service/                # Business logic & utilities
│   ├── JWTUtils.java
│   ├── OurUserDetailsService.java
│   ├── UserManagementService.java
│
└── UserManagementSystemApplication.java   # Main Spring Boot application
```

### Fronted (users-management-fronted/)
```
src/
│── components/
│   ├── auth/               # Authentication pages
│   │   ├── LoginPage.jsx
│   │   ├── RegistrationPage.jsx
│   │
│   ├── common/             # Shared components
│   │   ├── Footer.jsx
│   │   ├── Navbar.jsx
│   │
│   ├── userpage/           # User profile pages
│   │   ├── ProfilePage.jsx
│   │   ├── UpdateUser.jsx
│   │   ├── UserManagementPage.jsx
│
│── service/                # API service layer
│   └── UserService.js
│
├── App.js                  # Main React component
├── App.css                 # Styling
├── App.test.js             # React test cases
```

## Testing 
```
Backend:
cd UserManagementSystem
mvn test
Frontend:
cd users-management-fronted
npm test
```
## Future Improvements
- Implement role-based access control
- Add email verification during registration
- Add Docker Compose for seamless backend + frontend + database setup



