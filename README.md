## Tech Stack
- Java 21
- Spring Boot 3.2.0
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
