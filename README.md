# Microservices Learning Project

Spring Boot 3.5.6 + Spring Cloud 2025.0.0 + Java 21.

## Modules
- discovery-server: Eureka Server on 8761
- api-gateway: Spring Cloud Gateway on 8080
- order-service: CRUD + OpenFeign on 8081
- payment-service: CRUD on 8082
- inventory-service: CRUD + reservation on 8083

## Databases
Docker Compose starts three PostgreSQL 16 databases:
- orderdb: localhost:5433
- paymentdb: localhost:5434
- inventorydb: localhost:5435

Run from project root:
```bash
docker compose up -d
```

## IntelliJ run order
1. DiscoveryServerApplication
2. PaymentServiceApplication
3. InventoryServiceApplication
4. OrderServiceApplication
5. ApiGatewayApplication

Check Eureka: http://localhost:8761

## CRUD examples through Gateway
Orders:
- GET http://localhost:8080/api/orders
- GET http://localhost:8080/api/orders/{id}
- POST http://localhost:8080/api/orders
- PUT http://localhost:8080/api/orders/{id}
- DELETE http://localhost:8080/api/orders/{id}
- POST http://localhost:8080/api/orders/{id}/place

Create order:
```json
{"productId":1,"quantity":2,"amount":1000}
```

Payments:
- GET/POST/PUT/DELETE http://localhost:8080/api/payments

Create payment:
```json
{"orderId":1,"amount":1000}
```

Inventory:
- GET/POST/PUT/DELETE http://localhost:8080/api/inventory
- POST http://localhost:8080/api/inventory/reserve

Create inventory:
```json
{"productId":1,"quantityAvailable":10}
```

## Learning sequence
This project intentionally starts with simple synchronous communication and CRUD. Next layers can be added without replacing the foundation: Resilience4j timeout/retry/circuit breaker, idempotency, Saga, Kafka/ActiveMQ, outbox/DLQ, security, observability, Docker/Kubernetes.
