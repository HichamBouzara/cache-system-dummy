# Cache System

This project implements a cache system accessible via a REST API. The system allows storing and retrieving values in the cache, with automatic removal of entries after a specified TTL.

## Features

- **Cache Values**: Store and retrieve values via the API.
- **TTL Management**: Entries in the cache are automatically removed after a specified TTL.

## Project Architecture

- **Model**: The `Cache` class handles cache operations using a `ConcurrentHashMap` to ensure thread safety.
- **Service**: The `CacheService` class encapsulates cache logic and provides a clean interface for cache operations.
- **Controller**: The `CacheController` class exposes REST endpoints to interact with the cache system.

## How to Run the Project

### Clone the Repository

```bash
git clone https://github.com/HichamBouzara/cache-system-dummy.git
cd cache-system-dummy
```

### Build and Run the Project

```bash
mvn clean install
mvn spring-boot:run
```

### Test the Endpoints

You can use tools like Postman or curl to test the API endpoints.

#### Store a Value in the Cache:

```bash
POST "http://localhost:8080/cache/store?key=key1&value=value1"
```

#### Get a Value from the Cache:

```bash
GET "http://localhost:8080/cache/get?key=key1"
```

### Tests

The project includes both unit and integration tests to validate the functionality of the cache system.
