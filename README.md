# Financial House Reporting API Client

A reactive Spring Boot application that consumes Financial House Reporting API endpoints.

## 📋 Features

### Core Features
- Reactive API client implementation with WebClient
- Token-based authentication
- Transaction querying and reporting
- Integration tests with WebTestClient

### Technical Features
- Reactive programming with Spring WebFlux
- Exception handling with GlobalExceptionHandler
- Integration tests
- WebClient configuration

## 🛠 Tech Stack

### Core
- Java 17
- Spring Boot 3.x
- Spring WebFlux
- Project Reactor

### Testing
- JUnit 5
- WebTestClient
- Mockito

### Build Tools
- Maven 3.x
- Git

## 🚀 Getting Started

### Prerequisites
```bash
# Required
- JDK 17 or higher
- Maven 3.6.x or higher
- Git
```

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/reporting-api-client.git
cd reporting-api-client
```

2. Build the project
```bash
mvn clean install
```

### Running the Application
```bash
mvn spring-boot:run
```

## 📡 API Endpoints

### Authentication
```http
POST /api/v3/merchant/user/login
Content-Type: application/json

{
    "email": "demo@financialhouse.io",
    "password": "******"
}
```

### Transaction Operations

#### Query Transactions
```http
POST /api/v3/transaction/list
Authorization: Bearer {token}
Content-Type: application/json

{
    "fromDate": "2024-01-01",
    "toDate": "2024-01-31",
    "status": "APPROVED"
}
```

#### Generate Report
```http
POST /api/v3/transactions/report
Authorization: Bearer {token}
Content-Type: application/json

{
    "fromDate": "2024-01-01",
    "toDate": "2024-01-31"
}
```

#### Get Transaction Details
```http
POST /api/v3/transaction
Authorization: Bearer {token}
Content-Type: application/json

{
    "transactionId": "1-1444392550-1"
}
```

## 🏗 Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── finhouse/
│   │           ├── ReportingApiApplication.java
│   │           ├── client/
│   │           │   └── ReportingApiClient.java
│   │           ├── config/
│   │           │   └── WebConfig.java
│   │           ├── controller/
│   │           │   └── ReportController.java
│   │           ├── model/
│   │           │   ├── request/
│   │           │   └── LoginRequest.java
│   │           │   └── TransactionQueryRequest.java
│   │           │   └── TransactionReportRequest.java
│   │           │   └── TransactionRequest.java
│   │           │   └── response/
│   │           │   └── LoginResponse.java
│   │           │   └── TransactionQueryResponse.java
│   │           │   └── TransactionReportResponse.java
│   │           ├── service/
│   │           │   ├── AuthService.java
│   │           │   └── ReportService.java
│   │           └── exception/
│   │               └── ApiException.java
│   └── resources/
│       └── application.yml
└── test/
    └── java/
        └── com/
            └── finhouse/
                ├── service/
                ├── controller/
                └── integration/
```

## 🧪 Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ReportServiceTest

# Run integration tests
mvn test -Dtest=ReportingApiIntegrationTest
```

## 🔒 Security Features

- Token-based authentication
- Token expiration handling
- Request validation
- Error handling for invalid requests
