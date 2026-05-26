# Subscription Billing Platform

A production-inspired **multi-tenant Subscription Billing Platform backend** built with **Spring Boot**, designed using modular monolith architecture with a future roadmap toward microservices.

This project simulates a real-world SaaS billing backend where organizations can manage subscription plans, customer subscriptions, payments, invoices, and notifications.

---

## Project Vision

The goal of this project is to build a backend system similar in architecture to platforms like:

- Stripe Billing
- Chargebee
- Recurly
- Zoho Subscriptions

Current implementation focuses on strong backend engineering fundamentals:

- secure authentication
- clean architecture
- business rule enforcement
- concurrency handling
- pagination
- exception handling
- scalable modular design

Future iterations will evolve this into an event-driven distributed architecture.

---

## Architecture Strategy

### Current Architecture
**Modular Monolith**

Domain-based modular design:

```text
auth/
organization/
plan/
subscription/
user/
common/
config/
```

Benefits:

- clean separation of concerns
- easier testing
- scalable domain boundaries
- easier migration to microservices later

---

### Future Microservices Evolution

Planned architecture:

```text
API Gateway
Auth Service
Organization Service
Plan Service
Subscription Service
Payment Service
Invoice Service
Notification Service
Kafka Event Bus
```

Distributed system patterns planned:

- Saga Pattern
- Outbox Pattern
- Idempotent Consumers
- Dead Letter Queue (DLQ)
- Event-driven communication
- Retry resilience
- Circuit breaker protection

---

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Jakarta Validation

### Database
- PostgreSQL

### Security
- JWT Authentication
- Refresh Tokens
- Role-based authorization

### Build Tool
- Maven

### Tools
- Postman
- Git
- GitHub
- IntelliJ IDEA

---

## Implemented Features

### Authentication Module
Implemented:

- User Registration
- User Login
- JWT Access Token generation
- Refresh Token flow
- Protected endpoints
- Role-based security foundation

---

### Plan Management Module
Implemented:

- Create subscription plans
- Update plans (partial PATCH support)
- Deactivate plans
- Fetch plan by ID
- Paginated plan listing

Business rules enforced:

- duplicate plan names blocked
- inactive duplicate names blocked
- immutable fields protected
    - name
    - currency
- mutable fields patchable
    - monthlyPrice
    - yearlyPrice
    - features
    - trialDays
- deactivated plans cannot be updated
- plans with active subscriptions cannot be deactivated
- optimistic locking using `@Version`
- database uniqueness as final safeguard

---

## API Endpoints

### Auth APIs

```http
POST /api/v1/auth/register
POST /api/v1/auth/login
POST /api/v1/auth/refresh
```

---

### Plan APIs

```http
POST   /api/v1/plans
PATCH  /api/v1/plans/{planId}
PATCH  /api/v1/plans/{planId}/deactivate
GET    /api/v1/plans/{planId}
GET    /api/v1/plans?page=0&size=10
```

---

## Engineering Concepts Applied

This project intentionally demonstrates backend engineering concepts commonly expected in product company interviews:

### Data Layer
- JPA entity design
- optimistic locking
- pagination
- repository abstraction
- transactional boundaries

### API Design
- RESTful resource design
- PATCH semantics
- DTO-based API contracts
- validation

### Security
- stateless JWT authentication
- refresh token lifecycle
- secure endpoint protection

### Reliability
- centralized exception handling
- business validation layer
- concurrency conflict handling

### Architecture
- modular monolith
- domain-driven packaging
- microservices migration planning

---

## Local Setup

### Prerequisites

Install:

- Java 17
- Maven
- PostgreSQL

---

### Environment Variables

Configure:

```bash
JWT_SECRET=your-secret-key
DB_URL=jdbc:postgresql://localhost:5433/subscription_billing
DB_USERNAME=postgres
DB_PASSWORD=your-password
```

---

### Run Application

```bash
mvn clean install
mvn spring-boot:run
```

Application starts at:

```text
http://localhost:8080
```

---

## Project Roadmap

Planned next phases:

### Phase 1
- Swagger/OpenAPI documentation
- Unit tests
- Integration tests
- Docker setup
- Flyway migrations
- profile-based config

### Phase 2
Subscription lifecycle:

- create subscription
- cancel subscription
- upgrade/downgrade subscription
- trial handling

### Phase 3
Billing domain:

- payment processing
- invoice generation
- webhook handling

### Phase 4
Distributed systems:

- Kafka integration
- Outbox pattern
- Saga orchestration
- notification service
- Redis caching

---

## Interview Talking Points

This project demonstrates:

- backend architecture design
- secure authentication implementation
- business rule enforcement
- concurrency handling
- pagination
- modular code organization
- production-oriented backend thinking
- migration planning from monolith to microservices

---

## Status

Active development.