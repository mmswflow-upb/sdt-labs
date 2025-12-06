# University Management Microservices

Complete microservices architecture with Spring Cloud for managing university operations.

## Architecture

```
    Eureka (8761) → Service Registry
          ↓
    API Gateway (8080) → Routes + Circuit Breakers + Load Balancing
          ↓
    ┌─────┴─────┬─────────┬─────────┐
    ↓           ↓         ↓         ↓
Student(8081) Prof(8082) Course(8083) Grading(8084)
    ↓           ↓         ↓         ↓
  DB(5433)   DB(5434)  DB(5435)  DB(5436)
```

## Tech Stack

- **Spring Boot 3.2.0** + **Spring Cloud 2023.0.0**
- **Netflix Eureka** - Service discovery
- **Spring Cloud Gateway** - API gateway with circuit breakers
- **Spring Cloud OpenFeign** - Inter-service communication
- **Resilience4j** - Fault tolerance
- **PostgreSQL 16** - Separate DB per service
- **Docker Compose** - Orchestration

## Quick Start

```bash
# 1. Setup
cp .env.example .env

# 2. Run
docker compose up -d --build

# 3. Verify
curl http://localhost:8761  # Eureka dashboard
curl http://localhost:8080/students  # Via gateway
```

## Key Features

✅ **Service Discovery** - Services auto-register with Eureka, discover each other by name  
✅ **Dynamic Routing** - Gateway uses `lb://service-name` for load-balanced routing  
✅ **Circuit Breakers** - Resilience4j on all Feign clients + gateway routes  
✅ **Fallback Mechanisms** - Graceful degradation when services fail  
✅ **Health Monitoring** - Actuator endpoints + Eureka dashboard  

## Service Endpoints

**Via Gateway** (recommended):
```bash
curl http://localhost:8080/students
curl http://localhost:8080/professors
curl http://localhost:8080/courses
curl http://localhost:8080/grades
```

**Direct Access**:
- Student: http://localhost:8081
- Professor: http://localhost:8082
- Course: http://localhost:8083
- Grading: http://localhost:8084

## Configuration Highlights

### Eureka Server
```yaml
# Auto-registers all services on startup
# Dashboard: http://localhost:8761
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

### Microservices (as Eureka clients)
```yaml
spring:
  application:
    name: student-service  # Service ID in Eureka

eureka:
  client:
    service-url:
      defaultZone: ${EUREKA_SERVER_URL}
```

### Feign Clients (discovery-based)
```java
@FeignClient(
    name = "course-service",  // Discovers via Eureka, no hardcoded URL
    fallback = CourseServiceClientFallback.class
)
```

### Gateway Routes (load-balanced)
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: student-service
          uri: lb://student-service  # Load-balanced via Eureka
          predicates:
            - Path=/students/**
```

## Environment Variables

Key vars in `.env`:

```env
# Eureka
EUREKA_PORT=8761
EUREKA_SERVER_URL=http://eureka-server:8761/eureka/

# Services
API_GATEWAY_PORT=8080
STUDENT_SERVICE_PORT=8081
PROFESSOR_SERVICE_PORT=8082
COURSE_SERVICE_PORT=8083
GRADING_SERVICE_PORT=8084

# Databases
STUDENT_DB_PORT=5433
PROFESSOR_DB_PORT=5434
COURSE_DB_PORT=5435
GRADING_DB_PORT=5436
```

## Monitoring

### Eureka Dashboard
```bash
http://localhost:8761
# Shows: registered services, health, instance count, metadata
```

### Circuit Breaker Metrics
```bash
# Service-level circuit breakers
curl http://localhost:8081/actuator/circuitbreakers
curl http://localhost:8081/actuator/circuitbreakerevents

# Check state: CLOSED (ok), OPEN (failing), HALF_OPEN (testing recovery)
```

### Gateway Routes
```bash
curl http://localhost:8080/actuator/gateway/routes
```

## Testing

**Postman Collections**: See `postman-collections/` directory

**cURL Examples**:
```bash
# Create student
curl -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John", "lastName":"Doe", "email":"john@uni.ro"}'

# Get all students
curl http://localhost:8080/students

# Test circuit breaker - stop course-service and try:
curl http://localhost:8080/students/1
# Returns fallback data instead of error
```

## Development

**Run locally** (each service):
```bash
cd student-service
mvn spring-boot:run
```

**Database access**:
```bash
psql -h localhost -p 5433 -U student -d studentdb
# Password: student
```

**View logs**:
```bash
docker compose logs -f student-service
```

## How It Works

1. **Startup**: All services register with Eureka on startup
2. **Discovery**: Gateway + Feign clients fetch service registry from Eureka
3. **Routing**: Gateway routes requests using service names (`lb://student-service`)
4. **Load Balancing**: If multiple instances exist, requests are distributed
5. **Fault Tolerance**: Circuit breakers trip on failures, return fallback responses
6. **Health Checks**: Eureka monitors heartbeats, deregisters dead instances

## Troubleshooting

```bash
# Service not in Eureka
docker logs sdt-lab5-student-service | grep eureka

# Circuit breaker stuck OPEN
curl http://localhost:8081/actuator/circuitbreakers
# Wait for waitDurationInOpenState (5s), then HALF_OPEN → CLOSED

# Port conflicts
docker compose down && docker compose up -d
```

## Cleanup

```bash
docker compose down        # Stop services
docker compose down -v     # + Delete data
docker compose down --rmi all  # + Delete images
```

## Project Structure

```
Lab_5/
├── eureka-server/          # Service registry (8761)
├── api-gateway/            # Gateway + routing (8080)
├── student-service/        # Student API (8081)
├── professor-service/      # Professor API (8082)
├── course-service/         # Course API (8083)
├── grading-service/        # Grading API (8084)
├── postman-collections/    # API tests
├── docker-compose.yml      # Orchestration
└── .env.example            # Config template
```

## License

Educational project - UPB Software Development Techniques Lab 5
