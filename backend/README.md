#  Gestion Commande - Order Management System

A comprehensive Spring Boot REST API application for managing orders, products, suppliers, deliveries, and payments with JWT authentication and role-based access control.

## Features

### Core Functionality
- **Order Management**: Create, track, and validate orders with status monitoring
- **Product Catalog**: Manage products with stock tracking and low-stock alerts
- **Supplier Management**: Handle supplier information and product associations
- **Delivery Tracking**: Monitor order deliveries with transporteur management
- **Payment Processing**: Track payments associated with orders
- **User Management**: Role-based authentication (ADMIN/USER) with JWT tokens

### Technical Features
- RESTful API architecture
- JWT-based authentication and authorization
- Spring Security integration
- MySQL database with JPA/Hibernate
- CORS configuration for cross-origin requests
- Scheduled tasks support
- Comprehensive entity relationships

##  Technology Stack

- **Framework**: Spring Boot 2.7.18
- **Language**: Java 11
- **Database**: MySQL 8
- **ORM**: Hibernate/JPA
- **Security**: Spring Security + JWT (java-jwt 3.4.1)
- **Build Tool**: Maven
- **Additional Libraries**: 
  - Lombok (code generation)
  - Spring Boot DevTools (development)
  - MySQL Connector

##  Prerequisites

Before running this application, ensure you have:

- Java 11 or higher installed
- MySQL Server 8.x running
- Maven 3.6+ (or use the included Maven wrapper)
- IDE (Eclipse, IntelliJ IDEA, or VS Code)

##  Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd Gestion_Commande
```

### 2. Configure Database
Create a MySQL database:
```sql
CREATE DATABASE gestion_commande;
```

### 3. Update Application Properties
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/gestion_commande?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8090
```

### 4. Build the Project
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

Or run directly:
```bash
java -jar target/Gestion_Commande-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8090`

##  Database Schema

### Main Entities

| Entity | Description |
|--------|-------------|
| **User** | System users with roles (ADMIN/USER) |
| **Role** | User roles for access control |
| **Commande** | Orders with validation status |
| **Produit** | Products with stock management |
| **Fournisseur** | Suppliers providing products |
| **LigneCommande** | Order line items |
| **Livraison** | Delivery information |
| **Paiement** | Payment records |
| **Transporteur** | Delivery carriers |

### Entity Relationships
- User → Commande (One-to-Many)
- Commande → LigneCommande (One-to-Many)
- Commande → Livraison (One-to-Many)
- Commande → Paiement (One-to-Many)
- Fournisseur → Produit (One-to-Many)
- Produit → LigneCommande (One-to-Many)
- Transporteur → Livraison (One-to-Many)

##  Authentication

The application uses JWT (JSON Web Token) for authentication.


```

### Authentication Flow
1. POST `/login` with credentials
2. Receive JWT token in response
3. Include token in Authorization header: `Bearer <token>`
4. Access protected endpoints

## 📡 API Endpoints

### Authentication
```
POST /login          - User login
POST /register       - User registration
```

### Orders (Commandes)
```
GET    /api/commandes              - Get all orders
GET    /api/commandes/{id}         - Get order by ID
POST   /api/commandes              - Create new order
PUT    /api/commandes/{id}         - Update order
DELETE /api/commandes/{id}         - Delete order
```

### Products (Produits)
```
GET    /api/produits               - Get all products
GET    /api/produits/{id}          - Get product by ID
POST   /api/produits               - Create new product
PUT    /api/produits/{id}          - Update product
DELETE /api/produits/{id}          - Delete product
```

### Suppliers (Fournisseurs)
```
GET    /api/fournisseurs           - Get all suppliers
GET    /api/fournisseurs/{id}      - Get supplier by ID
POST   /api/fournisseurs           - Create new supplier
PUT    /api/fournisseurs/{id}      - Update supplier
DELETE /api/fournisseurs/{id}      - Delete supplier
```

### Deliveries (Livraisons)
```
GET    /api/livraisons             - Get all deliveries
GET    /api/livraisons/{id}        - Get delivery by ID
POST   /api/livraisons             - Create new delivery
PUT    /api/livraisons/{id}        - Update delivery
DELETE /api/livraisons/{id}        - Delete delivery
```

### Payments (Paiements)
```
GET    /api/paiements              - Get all payments
GET    /api/paiements/{id}         - Get payment by ID
POST   /api/paiements              - Create new payment
PUT    /api/paiements/{id}         - Update payment
DELETE /api/paiements/{id}         - Delete payment
```

### Users
```
GET    /api/users                  - Get all users (ADMIN)
GET    /api/users/{id}             - Get user by ID
POST   /api/users                  - Create new user
PUT    /api/users/{id}             - Update user
DELETE /api/users/{id}             - Delete user
```

##  Project Structure

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── Controller/          # REST Controllers
│   │   ├── model/               # Entity classes
│   │   ├── repos/               # Repository interfaces
│   │   ├── service/             # Service layer (interfaces & implementations)
│   │   ├── security/            # Security configuration & JWT filters
│   │   └── GestionCommandeApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/example/demo/
        └── GestionCommandeApplicationTests.java
```

## Testing

Run tests using Maven:
```bash
mvn test
```
