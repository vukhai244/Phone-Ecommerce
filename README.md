# E-Commerce Phone System Using Microservice

## Introduction
This project is an e-commerce platform designed for selling phones. The system is built using a microservices architecture to ensure scalability, flexibility, and maintainability. Each service is responsible for a specific domain, making the system modular and easier to develop, deploy, and maintain.

## Architecture Overview
The system is composed of multiple microservices, each handling a specific function within the e-commerce platform:

- **Account Service**: Manages user accounts
- **Phone Service**: Handles product catalog, including phone details, categories, and inventory management.
- **Order Service**: Manages customer orders, order processing.
- **Cart Service**: Manages the shopping cart functionality.
- **API Gateway**: Routes requests to the appropriate microservice and handles load balancing.
- **Config Service**: Provides centralized configuration management for all microservices. It allows dynamic configuration updates and centralizes configuration properties to ensure consistency across environments.
- **Service Discovery (Eureka)**: Allows microservices to discover each other and handle dynamic scaling.
- **Load Balancer**: Specifically designed to distribute incoming order requests across multiple instances of the Order Service. This ensures that high traffic and concurrent order placements are efficiently managed, improving the system's reliability and responsiveness.
- **MySQL Server**: A relational database management system (RDBMS) used to store persistent data for the system, including user data, product information, orders, and transactions. 
- **RabbitMQ**: Send promotional offer notifications to users via email.
- **Redis-Data**: A caching layer used to handle high concurrent access and improve system performance during peak times, such as promotional days when many users are placing orders simultaneously. 

## Key Technologies
- **Java Spring Boot**: Used for developing the microservices.
- **Spring Cloud**: Provides tools for building microservice architectures, including service discovery (Eureka), load balancing, and API Gateway.
- **RabbitMQ**: For asynchronous messaging between microservices.
- **Redis**: Used as a caching layer to handle high concurrent access and improve system performance during peak times.
- **MySQL**: Databases used for data persistence.
- **Docker**: Used for containerizing microservices.

## Getting Started
### Prerequisites
- **Java 17** or higher
- **Docker** and **Docker Compose**
- **Maven** 
- **MySQL** 
- **RabbitMQ** for messaging
