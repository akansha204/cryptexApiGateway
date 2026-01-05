# Cryptex API Gateway

Cryptex API Gateway is the single entry point for all client requests in the Cryptex microservices system.

It is responsible for routing requests to internal services, validating authentication tokens, and forwarding verified user context to downstream services.

---

## Responsibilities

- Acts as a centralized entry point for all APIs
- Validates JWT tokens before forwarding requests
- Extracts user details (userId, email) from JWT claims
- Adds user information to request headers
- Routes requests to appropriate backend services
- Prevents direct access to internal services

---

## Request Flow

Client  
→ API Gateway  
→ Authentication Validation  
→ Target Microservice (Auth / Secret )

---

## Authentication Handling

- Incoming requests must include a JWT token
- The gateway verifies the token using the shared secret
- On successful verification, user information is added to headers:
    - `X-User-Id`
    - `X-User-Email`
- Downstream services trust the gateway and do not revalidate JWTs

---

## Services Routed

- Auth Service
- Secret Service

---

## Why API Gateway

- Centralized security enforcement
- Simplified client-side API usage
- Clear separation between external and internal services
- Improved scalability and maintainability

---

## Tech Stack

- Language: Java
- Framework: Spring Boot
- Security: JWT
- Architecture: Microservices
- Dependencies - Reactive Gateway, Spring Security, OAuth2 Resource Server, SpringBoot Actuator (optional)

---

## Notes

All internal services are designed to accept requests only from the API Gateway.  
Direct access to services is restricted to ensure system security.
