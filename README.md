# Subscription Billing Platform

A production-grade Subscription Billing Platform built using modern Java backend technologies, designed to demonstrate scalable microservice-ready architecture, event-driven communication, observability, monitoring, caching, and secure authentication.

## Overview

This platform enables users to subscribe to plans, manage subscriptions, process payments, and automatically renew subscriptions. It incorporates industry-standard practices such as JWT authentication, Redis caching, Kafka event streaming, Dockerized deployment, and a complete observability stack using Prometheus and Grafana.

---

## Tech Stack

### Backend

* Java 17
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Hibernate

### Database

* PostgreSQL

### Caching

* Redis

### Messaging

* Apache Kafka

### Security

* JWT Authentication
* Role-Based Authorization

### Monitoring & Observability

* Spring Boot Actuator
* Micrometer
* Prometheus
* Grafana

### API Documentation

* Swagger / OpenAPI

### Containerization

* Docker
* Docker Compose

### Testing

* JUnit 5
* Mockito
* Testcontainers (Upcoming)

---

## Features

### User Management

* User Registration
* User Login
* JWT Token Generation
* Secure Endpoints
* Role-Based Access Control

### Plan Management

* Create Subscription Plans
* Update Plans
* Delete Plans
* Retrieve Plan Details

### Subscription Management

* Create Subscription
* Activate Subscription
* Cancel Subscription
* Retrieve Subscription Information

### Payment Processing

* Payment Initiation
* Payment Tracking
* Payment Status Management
* Payment Success Metrics

### Auto-Renewal Engine

* Scheduled Subscription Renewal
* Automatic Renewal Processing
* Renewal Event Generation

### Event-Driven Architecture

* Kafka Producer Integration
* Kafka Consumer Integration
* Subscription Events
* Payment Events

### Caching Layer

* Redis Integration
* Faster Data Retrieval
* Reduced Database Load

### Monitoring & Observability

* Application Health Monitoring
* JVM Metrics
* HTTP Request Metrics
* Custom Business Metrics
* Grafana Dashboards

---

## System Architecture

```text
                    +----------------------+
                    |      Client/API      |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    | Spring Boot Backend  |
                    +----------+-----------+
                               |
        +----------------------+----------------------+
        |                      |                      |
        v                      v                      v

+---------------+    +----------------+    +----------------+
| PostgreSQL    |    | Redis Cache    |    | Kafka Broker   |
| Persistence   |    | Fast Access    |    | Event Stream   |
+---------------+    +----------------+    +----------------+

                               |
                               v

                    +----------------------+
                    | Micrometer Metrics   |
                    +----------+-----------+
                               |
                               v

                    +----------------------+
                    | Prometheus           |
                    +----------+-----------+
                               |
                               v

                    +----------------------+
                    | Grafana Dashboard    |
                    +----------------------+
```

---

## Project Structure

```text
subscription-billing-platform
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.subscription.billing
│   │   └── resources
│   │
│   └── test
│
├── monitoring
│   ├── prometheus
│   │   └── prometheus.yml
│   │
│   └── grafana
│       └── subscription-billing-dashboard.json
│
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

---

## Security

Authentication is implemented using JWT tokens.

### Authentication Flow

```text
User Login
     |
     v
Generate JWT
     |
     v
Client Stores Token
     |
     v
Authorization Header
     |
     v
Protected API Access
```

Example Header:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI Specification:

```text
http://localhost:8080/v3/api-docs
```

---

## Running Locally

### Prerequisites

* Java 17
* Maven
* Docker Desktop
* Git

### Clone Repository

```bash
git clone https://github.com/Kr11ssb/subscription-billing-platform.git
cd subscription-billing-platform
```

### Build Application

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

---

## Running with Docker

Build Application:

```bash
docker compose build
```

Start Services:

```bash
docker compose up -d
```

Stop Services:

```bash
docker compose down
```

---

## Available Services

| Service     | URL                                   |
| ----------- | ------------------------------------- |
| Application | http://localhost:8080                 |
| Swagger UI  | http://localhost:8080/swagger-ui.html |
| PostgreSQL  | localhost:5432                        |
| Redis       | localhost:6379                        |
| Kafka       | localhost:9092                        |
| Prometheus  | http://localhost:9090                 |
| Grafana     | http://localhost:3000                 |

---

## Monitoring Stack

### Spring Boot Actuator

Health Endpoint:

```text
http://localhost:8080/actuator/health
```

Metrics Endpoint:

```text
http://localhost:8080/actuator/prometheus
```

---

## Prometheus

Configured to scrape application metrics from:

```text
/actuator/prometheus
```

Target Status:

```text
UP
```

---

## Grafana Dashboard

Dashboard Includes:

* Total Subscriptions
* Successful Payments
* Subscription Renewals
* JVM Memory Usage
* JVM Threads
* HTTP Request Metrics
* Application Health Metrics

Dashboard JSON Location:

```text
monitoring/grafana/subscription-billing-dashboard.json
```

---

## Custom Business Metrics

The platform exposes custom Micrometer metrics:

### Total Subscriptions

```text
subscription_total
```

Tracks the number of subscriptions created.

### Successful Payments

```text
payment_success_total
```

Tracks successful payment transactions.

### Subscription Renewals

```text
subscription_renewal_total
```

Tracks completed subscription renewals.

---

## Observability Flow

```text
Spring Boot
     |
Micrometer
     |
Actuator
     |
Prometheus
     |
Grafana
```

Business metrics are verified end-to-end.

---

## Future Enhancements

* Testcontainers Integration Testing
* GitHub Actions CI/CD Pipeline
* Kubernetes Deployment
* Distributed Tracing
* OpenTelemetry Integration
* Multi-Tenant Subscription Support
* Stripe Payment Gateway Integration
* Notification Service
* Email Service
* Audit Logging
* Circuit Breakers
* Rate Limiting

---

## Learning Outcomes

This project demonstrates:

* Production-Grade Spring Boot Development
* Secure Authentication with JWT
* REST API Design
* Event-Driven Architecture
* Kafka Messaging
* Redis Caching
* Docker Containerization
* Monitoring and Observability
* Metrics Collection
* Dashboarding with Grafana
* PostgreSQL Database Design
* Clean Architecture Principles

---

## Author

Karan Rabde

Senior Software Engineer | Java Backend Developer

LinkedIn: Add Your LinkedIn URL

GitHub: https://github.com/Kr11ssb
