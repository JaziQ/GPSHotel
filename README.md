## Tech Stack
- Java 25
- Spring Boot 4.0.0
- Spring Data JPA
- H2 Database (in-memory, default)
- MySQL(optional)
- Liquibase 
- Swagger UI
- Maven
- JUnit 5 / Mockito

## Quick Start

### 1. Clone and run
```bash
git clone https://github.com/JaziQ/GPSHotel
cd GPSHotel
mvn clean package
mvn spring-boot:run
```

## To use MySQL database

#### Comment H2 section and uncomment MySQL section in application.properties
