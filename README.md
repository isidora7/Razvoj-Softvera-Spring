# Student Service Backend

Backend application for a student administration system built with **Java and Spring Boot**.

The project models core university administration workflows such as students, courses, exams, study programs, teachers, academic years, and payments. It follows a layered architecture with controllers, services, repositories, domain models, DTO mapping utilities, and automated tests.

## Tech Stack

- **Java 11**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Spring Data REST**
- **Spring Security**
- **MySQL**
- **H2 Database**
- **Maven**
- **Lombok**
- **Mockito**
- **JUnit 5**
- **Java Faker**

## Main Features

The application includes backend functionality for managing:

- Students
- Study programs
- Courses and subjects
- Teachers
- Academic years
- Exams
- Payments
- Course assignments
- Student index data

The backend exposes REST endpoints through Spring controllers and uses a service layer for business logic.

## Project Architecture

The application follows a layered backend structure:

```text
src/main/java/org/raflab/studsluzba/
├── config/
├── controllers/
│   ├── request/
│   └── response/
├── model/
├── repositories/
├── services/
├── utils/
│   ├── converters/
│   └── mappers/
├── Seeder.java
└── StudsluzbaServerApp.java
```

### Controllers

Controllers expose REST endpoints and handle incoming HTTP requests.

The project includes controllers for areas such as:

- Students
- Exams
- Subjects
- Teachers
- Study programs
- Academic years
- Payments
- Courses

### Services

The service layer contains business logic and coordinates communication between controllers and repositories.

This keeps business rules separate from HTTP request handling and database access.

### Repositories

Repository classes use Spring Data JPA to handle persistence and database operations.

### Models

The model layer represents the main domain entities used by the student administration system.

### DTOs and Mapping

Request and response objects are separated from persistence models.

Utility classes, converters, and mappers are used to transform data between API models and domain entities.

## Database

The application uses **Spring Data JPA** for persistence.

The project includes support for:

- **MySQL** as the main relational database
- **H2** as an additional lightweight runtime database option

Database configuration can be provided through environment-specific application settings.

## Security

The project includes **Spring Security** configuration through:

```text
config/SecurityConfig.java
```

This provides a foundation for securing backend endpoints and controlling application access.

## Test Data

The application includes a `Seeder` component used to populate the database with initial or generated data.

**Java Faker** is also included for generating realistic test data.

## Automated Tests

The project contains unit tests for service and utility logic.

Examples include:

```text
src/test/java/org/raflab/studsluzba/
├── services/
│   ├── DrziPredmetServiceTest.java
│   └── StudentIndeksServiceTest.java
└── utils/
    └── ParseUtilsTest.java
```

The tests use **JUnit 5** and **Mockito** to validate business logic independently from external dependencies.

## Running the Project

### Clone the repository

```bash
git clone https://github.com/isidora7/Razvoj-Softvera-Spring.git
cd Razvoj-Softvera-Spring
```

### Build the project

```bash
mvn clean install
```

### Run the application

```bash
mvn spring-boot:run
```

### Run automated tests

```bash
mvn test
```

## Project Purpose

This project was developed to practice and demonstrate backend software development concepts using Java and Spring Boot, including:

- REST API development
- Layered application architecture
- Business logic implementation
- Database persistence with JPA
- Repository and service patterns
- DTO mapping
- Spring Security configuration
- Unit testing with JUnit and Mockito
- Relational database integration
