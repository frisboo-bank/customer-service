# Project

Kotlin + Spring Boot service using Gradle. This repository contains domain models, sagas and tests (including
property-based tests using Kotest).

## Tech stack

- Kotlin
- Spring Boot
- Gradle
- Kotest (unit + property tests)
- SQL (DB access)

## Prerequisites

- JDK 17+
- Gradle (wrapper included)
- macOS (development environment)

## Build

Run the Gradle wrapper:
./gradlew clean build

## Run

Start the Spring Boot app:
./gradlew bootRun

Run unit and property tests:
./gradlew test

## Schemas

### Customer Registration Sequence Diagram

```mermaid
sequenceDiagram
    actor Customer
    participant API as Customer API
    participant CustomerService as Customer Service
    participant CustomerRepository as Customer Repository
    participant SagaRepository as Saga Repository
    participant WriteDB as Customer WriteDB as PostgresSQL
    participant Idempotency as Idempotency Store
    participant Kafka as Kafka
    participant Locker as Redis Lock
    participant ReadDB as Customer ReadDB as MongoDB
%% Phase 1: Registration Draft Creation
    Customer ->> API: POST /customers/registration<br/>{ addresses:{...}, emails:{...}, ... }<br/>Idempotency-Key: idem-123
    API ->> CustomerService: createCustomerRegistrationDraft(request, idem-123)
    CustomerService ->> Idempotency: check("registerCustomer", "idem-123")
    Idempotency -->> CustomerService: null (not found)
%% Do some pre validation
    CustomerService ->> CustomerRepository: validateEmailAvailability(email)
    CustomerRepository ->> WriteDB: SELECT 1 FROM emails WHERE email = ? AND deleted_at IS NULL FOR UPDATE
    WriteDB -->> CustomerRepository: No results
    CustomerRepository -->> CustomerService: Available
%% Acquire distributed lock
    CustomerService ->> Locker: acquireLock("email:customer@email.com", ttl=120s)
    Locker -->> CustomerService: Acquired
%% Create draft saga with 24hr expiration
    CustomerService ->> SagaRepository: createSagaDraft(commandPayload, expiresIn=24h)
    SagaRepository ->> WriteDB: INSERT INTO sagas (<br/>command_type, command_payload, saga_status,..., expires_at) VALUES ('CustomerRegistration', {...}, 'DRAFT',...,<br/>NOW() + INTERVAL '24 hours'<br/>)
    WriteDB -->> SagaRepository: Inserted
    SagaRepository -->> CustomerService: Returns created saga
%% Release distributed lock
    CustomerService ->> Locker: releaseLock("email:customer@email.com") %% CRITICAL!
    Locker -->> CustomerService: Released
%% Return saga to Customer
    CustomerService -->> API: return created saga
    API -->> Customer: 202 Accepted<br/>{ transactionId: ..., addresses:{...}, emails:{...}, ..., expires_at: "2025-01-01T12:00:00Z",<br/>approvalLink: /customers/registration/approve?transactionId=...<br/>resumeLink: /customers/registration/resume?transactionId=... }
%% Phase 2: Registration Approval
    Customer ->> API: POST /customers/registration/approve?transactionId:...<br/>{ addresses:{...}, emails:{...}, ..., expires_at: "2025-01-01T12:00:00Z" }
    API ->> CustomerService: executeCustomerRegistration(transactionId, request)
%% Validate Saga existence and status
    CustomerService ->> SagaRepository: getSagaByTransactionId(transactionId)
    SagaRepository ->> WriteDB: SELECT * FROM sagas WHERE saga_id = ? AND saga_status = "DRAFT" FOR UPDATE
    WriteDB -->> SagaRepository: Returns saga with status DRAFT
    SagaRepository -->> CustomerService: saga found with status DRAFT
%% Acquire distributed lock
    CustomerService ->> Locker: acquireLock("email", ttl=300s)
    Locker -->> CustomerService: lock acquired
%% Do some pre validation
    CustomerService ->> CustomerRepository: validateEmailAvailability(email)
    CustomerRepository ->> WriteDB: SELECT 1 FROM emails WHERE email = ? AND deleted_at IS NULL FOR UPDATE
    WriteDB -->> CustomerRepository: No existing email found
    CustomerRepository -->> CustomerService: email still available
%% Begin Saga execution
    CustomerService ->> SagaRepository: startSagaExecution(transactionId)
```
