# Todo List API

A clean **Spring Boot REST API** for managing todo items and users.  
It ships with **OpenAPI/Swagger UI**, **profile-based environment configuration** (`dev` / `prod`) using `.env` files, **Flyway** for DB migrations, and **Maven + JaCoCo** for tests & coverage.

## Table of Contents
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Environment Setup](#environment-setup)
  - [.env files](#env-files)
  - [Spring profiles and config import](#spring-profiles-and-config-import)
- [Run Locally](#run-locally)
- [API Docs (Swagger)](#api-docs-swagger)
- [Database & Migrations](#database--migrations)
- [Testing & Coverage](#testing--coverage)
  - [Maven + JaCoCo](#maven--jacoco)
  - [Open the coverage report](#open-the-coverage-report)
- [License](#license)

---

## Tech Stack
- Java 17
- Spring Boot 3.x (Web, Data JPA, Validation, Security)
- Springdoc OpenAPI (Swagger UI)
- PostgreSQL + Flyway
- Maven (build & test)
- JaCoCo (coverage)

## Project Structure
```
todo-list-api/
 ├─ src/
 │   ├─ main/
 │   │   ├─ java/... (controllers, services, repositories, entities)
 │   │   └─ resources/
 │   │       ├─ application.yaml
 │   │       ├─ application-dev.yaml
 │   │       └─ application-prod.yaml
 │   └─ test/
 ├─ .env.example
 ├─ .env.dev        # ignored by Git
 ├─ .env.prod       # ignored by Git
 └─ pom.xml
```

---

## Environment Setup

### .env files
Copy the example file and fill your values:

```bash
cp .env.example .env.dev
cp .env.example .env.prod
```

**Suggested keys** (adjust names if your code expects different ones):

```dotenv
# .env.example
DB_URL=jdbc:postgresql://localhost:5432/todo_dev
DB_USER=postgres
DB_PASSWORD=postgres
JWT_SECRET=change_me_dev_secret
SERVER_PORT=8080
```

Typical **prod** values (example):
```dotenv
# .env.prod
DB_URL=jdbc:postgresql://db-server:5432/todo
DB_USER=todo_user
DB_PASSWORD=superSecretProd!
JWT_SECRET=super_strong_prod_secret
SERVER_PORT=8080
```

> Keep `.env.dev` and `.env.prod` **out of Git**. Only commit `.env.example`.

### Spring profiles and config import
This project uses profile-specific imports so each profile reads a different `.env`:

```yaml
# application-dev.yaml
spring:
  config:
    import: optional:file:.env.dev[.properties]
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
server:
  port: ${SERVER_PORT:8080}
```

```yaml
# application-prod.yaml
spring:
  config:
    import: optional:file:.env.prod[.properties]
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
server:
  port: ${SERVER_PORT:8080}
```

> Do **not** set `spring.profiles.active` inside `application-*.yaml`. Activate profiles via CLI or environment variables.

---

## Run Locally

**Dev profile** (recommended for local):
```bash
# from the project root
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Prod profile** (for a production-like run):
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

Or with a fat JAR:
```bash
mvn clean package
java -jar target/todo-list-api-*.jar --spring.profiles.active=dev
```

---

## API Docs (Swagger)

Once the app is running, open:

- Swagger UI:  
  - "[PATH]:[PORT]/api/docs"

> Endpoints and schemas are fully discoverable there. You can execute requests directly from the browser.

---

## Database & Migrations

This project uses **Flyway**. On startup, migrations in `src/main/resources/db/migration` are applied automatically.

- If you add a new column/constraint, create a new migration file:
  ```
  src/main/resources/db/migration/VXX__meaningful_name.sql
  ```
- For existing databases, ensure `flyway_schema_history` table is present and the app has rights to run migrations.

---

## Testing & Coverage

### Maven + JaCoCo

Run unit/integration tests:
```bash
mvn test
```

Generate coverage with JaCoCo:
```bash
mvn clean verify
```

> You can also enforce minimum coverage with `jacoco:check` rules if desired.

### Open the coverage report
After `mvn verify`, open:
```
target/site/jacoco/index.html
```

---

## License
This project is distributed under the terms described in the `LICENSE` file (add one if needed).
