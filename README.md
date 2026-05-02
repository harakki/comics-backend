# harakki-comics - service for reading graphic literature

[Версия на русском языке также доступна.](README_RUS.md)

Backend for the diploma project "Development of a web service for reading graphic literature". Implemented as a *
*modular monolith** on Spring Boot 4 and Java 25.

## Contents

- [Technology stack](#technology-stack)
- [Architecture](#architecture)
- [REST API](#rest-api)
- [Run](#run)
    - [Requirements](#requirements)
    - [Local run](#local-run)
        - [Dev profile](#dev-profile)
        - [Prod profile](#prod-profile)
    - [Docker image build](#docker-image-build)
- [Environment variables (prod profile)](#environment-variables-prod-profile)
- [OpenAPI / Scalar UI](#openapi--scalar-ui)
- [License](#license)

---

## Technology stack

| Component        | Technology                         |
|------------------|------------------------------------|
| Language         | Java 25                            |
| Framework        | Spring Boot 4                      |
| Architecture     | Spring Modulith (modular monolith) |
| Database         | PostgreSQL 18                      |
| Media storage    | S3-compatible MinIO DB             |
| Authentication   | Keycloak 26 (OAuth2 / JWT)         |
| Build            | Gradle (Kotlin DSL)                |
| Containerization | Docker / Jib                       |
| API docs         | SpringDoc OpenAPI 3 + Scalar UI    |
| Model mapping    | MapStruct                          |

---

## Architecture

C4 diagram of the application modules:

![C4 diagram](misc/images/c4-modules-diagram.png)

The application is divided into the following Spring Modulith modules:

| Module            | Package                              | Responsibility                                       |
|-------------------|--------------------------------------|------------------------------------------------------|
| `catalog`         | `dev.harakki.comics.catalog`         | Titles (works), authors, publishers, and tags        |
| `content`         | `dev.harakki.comics.content`         | Chapters, pages, user read history                   |
| `library`         | `dev.harakki.comics.library`         | User personal library                                |
| `collections`     | `dev.harakki.comics.collections`     | User collections with privacy parameters             |
| `analytics`       | `dev.harakki.comics.analytics`       | View and interaction statistics                      |
| `recommendations` | `dev.harakki.comics.recommendations` | Personalized title recommendations                   |
| `media`           | `dev.harakki.comics.media`           | Media file management via S3 (MinIO)                 |
| `shared`          | `dev.harakki.comics.shared`          | Shared utilities, exceptions, and application config |

---

## REST API

Default URL: `http://localhost:8080`

Interactive documentation is available at: [
`http://localhost:8080/scalar/index.html`](http://localhost:8080/scalar/index.html)

### Titles - `/api/v1/titles`

| Method   | Path                  | Access | Description              |
|----------|-----------------------|--------|--------------------------|
| `GET`    | `/api/v1/titles`      | Public | Search and filter titles |
| `GET`    | `/api/v1/titles/{id}` | Public | Get title by UUID        |
| `POST`   | `/api/v1/titles`      | ADMIN  | Create title             |
| `PATCH`  | `/api/v1/titles/{id}` | ADMIN  | Update title             |
| `DELETE` | `/api/v1/titles/{id}` | ADMIN  | Delete title             |

### Chapters - `/api/v1/titles` + `/api/v1/chapters`

| Method   | Path                                       | Access | Description                                   |
|----------|--------------------------------------------|--------|-----------------------------------------------|
| `GET`    | `/api/v1/titles/{titleId}/chapters`        | Public | List of title chapters (brief information)    |
| `GET`    | `/api/v1/titles/{titleId}/next-chapter`    | USER   | Next unread chapter for this title            |
| `GET`    | `/api/v1/chapters/{chapterId}`             | Public | Full chapter (including page URLs)            |
| `GET`    | `/api/v1/chapters/{chapterId}/read-status` | USER   | Check whether chapter is read by current user |
| `POST`   | `/api/v1/titles/{titleId}/chapters`        | ADMIN  | Create chapter                                |
| `POST`   | `/api/v1/chapters/{chapterId}/read`        | USER   | Mark chapter as read and get the next one     |
| `PATCH`  | `/api/v1/chapters/{chapterId}`             | ADMIN  | Update chapter metadata                       |
| `DELETE` | `/api/v1/chapters/{chapterId}`             | ADMIN  | Delete chapter                                |

### Authors - `/api/v1/authors`

| Method   | Path                   | Access | Description               |
|----------|------------------------|--------|---------------------------|
| `GET`    | `/api/v1/authors`      | Public | Search and filter authors |
| `GET`    | `/api/v1/authors/{id}` | Public | Get author by UUID        |
| `POST`   | `/api/v1/authors`      | ADMIN  | Create author             |
| `PATCH`  | `/api/v1/authors/{id}` | ADMIN  | Update author             |
| `DELETE` | `/api/v1/authors/{id}` | ADMIN  | Delete author             |

### Publishers - `/api/v1/publishers`

| Method   | Path                      | Access | Description                  |
|----------|---------------------------|--------|------------------------------|
| `GET`    | `/api/v1/publishers`      | Public | Search and filter publishers |
| `GET`    | `/api/v1/publishers/{id}` | Public | Get publisher by UUID        |
| `POST`   | `/api/v1/publishers`      | ADMIN  | Create publisher             |
| `PATCH`  | `/api/v1/publishers/{id}` | ADMIN  | Update publisher             |
| `DELETE` | `/api/v1/publishers/{id}` | ADMIN  | Delete publisher             |

### Tags - `/api/v1/tags`

| Method   | Path                | Access | Description                        |
|----------|---------------------|--------|------------------------------------|
| `GET`    | `/api/v1/tags`      | Public | List of all tags (with pagination) |
| `GET`    | `/api/v1/tags/{id}` | Public | Get tag by UUID                    |
| `POST`   | `/api/v1/tags`      | ADMIN  | Create tag                         |
| `PATCH`  | `/api/v1/tags/{id}` | ADMIN  | Update tag                         |
| `DELETE` | `/api/v1/tags/{id}` | ADMIN  | Delete tag                         |

### Library - `/api/v1/library`

| Method   | Path                               | Access | Description                  |
|----------|------------------------------------|--------|------------------------------|
| `GET`    | `/api/v1/library`                  | USER   | Get my library               |
| `GET`    | `/api/v1/library/{titleId}`        | USER   | Library entry by title ID    |
| `PUT`    | `/api/v1/library/titles/{titleId}` | USER   | Add / update a library entry |
| `DELETE` | `/api/v1/library/{entryId}`        | USER   | Delete a library entry       |
| `GET`    | `/api/v1/users/{userId}/library`   | Public | User public library          |

### Collections - `/api/v1/collections`

| Method   | Path                                        | Access        | Description                                        |
|----------|---------------------------------------------|---------------|----------------------------------------------------|
| `GET`    | `/api/v1/collections`                       | Public        | Search public collections                          |
| `GET`    | `/api/v1/collections/{id}`                  | Public / USER | Get collection by UUID (public ones without token) |
| `GET`    | `/api/v1/collections/my`                    | USER          | My collections                                     |
| `POST`   | `/api/v1/collections`                       | USER          | Create collection                                  |
| `PATCH`  | `/api/v1/collections/{id}`                  | USER          | Update collection                                  |
| `DELETE` | `/api/v1/collections/{id}`                  | USER          | Delete collection                                  |
| `POST`   | `/api/v1/collections/{id}/titles/{titleId}` | USER          | Add a title to collection                          |
| `DELETE` | `/api/v1/collections/{id}/titles/{titleId}` | USER          | Remove a title from collection                     |

### Analytics - `/api/v1/titles`

| Method | Path                                    | Access | Description                               |
|--------|-----------------------------------------|--------|-------------------------------------------|
| `GET`  | `/api/v1/titles/{titleId}/analytics`    | Public | Analytics for title                       |
| `GET`  | `/api/v1/analytics/titles/top-weekly`   | Public | Top 10 popular titles for the last 7 days |
| `GET`  | `/api/v1/analytics/titles/top-all-time` | Public | Top 10 popular titles of all time         |

### Recommendations - `/api/v1/recommendations`

| Method | Path                                      | Access | Description                               |
|--------|-------------------------------------------|--------|-------------------------------------------|
| `GET`  | `/api/v1/recommendations/me`              | USER   | Personal recommendations for current user |
| `GET`  | `/api/v1/titles/{titleId}/similar-titles` | Public | Similar titles for given title            |

### Media - `/api/v1/media`

| Method   | Path                       | Access | Description                                               |
|----------|----------------------------|--------|-----------------------------------------------------------|
| `GET`    | `/api/v1/media/{id}/url`   | Public | Get presigned URL for file download                       |
| `POST`   | `/api/v1/media/upload-url` | ADMIN  | Generate presigned URL for uploading a file to the server |
| `DELETE` | `/api/v1/media/{id}`       | ADMIN  | Delete media file                                         |

---

## Run

### Requirements

To run the project, the following components must be installed:

- **Docker** and **Docker Compose**
- **JDK 25** ([Eclipse Temurin](https://adoptium.net/) or any other distribution) for local run (optional, only for
  backend without containerization)

### Docker Compose run (recommended)

**To run this application you should run in root of the repository this command**

```bash
docker compose up --build
```

### Local run

**To run this application on local machine without containerization (only for backend part) run in root of the
repository this command**

```bash
./gradlew bootRun
```

### Services

After startup, services are available at:

| Service              | URL                                     |
|----------------------|-----------------------------------------|
| API (Backend)        | http://localhost:8080                   |
| Scalar UI (API Docs) | http://localhost:8080/scalar/index.html |
| Swagger UI           | http://localhost:8080/swagger-ui.html   |
| Keycloak Admin       | http://localhost:8082                   |
| MinIO Console        | http://localhost:9001                   |
| PostgreSQL           | localhost:5432                          |

#### Prod profile

Prod profile setup requires external services (PostgreSQL, MinIO, and Keycloak) and proper configuration of project
environment variables (see section ["Environment variables (prod profile)"](#environment-variables-prod-profile)).

**Run application**

```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

Or via a ready Docker image (see section ["Docker image build"](#docker-image-build)):

### Docker image build

The project uses [Jib](https://github.com/GoogleContainerTools/jib) to build the application image.

**Build local Docker image**

```bash
./gradlew jibDockerBuild
```

**Run container**

```bash
docker run -p 8080:8080 \
  -e ... # specify required environment variables for prod profile \
  harakki-comics:latest
```

---

## Environment variables (prod profile)

| Variable                 | Description                                | Example                                                                  |
|--------------------------|--------------------------------------------|--------------------------------------------------------------------------|
| `DATABASE_URL`           | JDBC database URL                          | `jdbc:postgresql://localhost:5432/comics-db`                             |
| `DATABASE_USERNAME`      | DB username                                | `myuser`                                                                 |
| `DATABASE_PASSWORD`      | DB user password                           | `secret`                                                                 |
| `S3_REGION`              | S3/MinIO region (default `eu-central-1`)   | `eu-central-1`                                                           |
| `S3_ENDPOINT`            | S3/MinIO endpoint                          | `http://minio:9000`                                                      |
| `S3_ACCESS_KEY`          | S3 Access Key                              | `minioadmin`                                                             |
| `S3_SECRET_KEY`          | S3 Secret Key                              | `minioadmin`                                                             |
| `S3_BUCKET`              | S3 bucket name                             | `comics-bucket`                                                          |
| `JWT_ISSUER_URI`         | JWT issuer URI (Keycloak realm)            | `http://keycloak:8081/realms/comics-realm`                               |
| `JWT_JWK_SET_URI`        | Keycloak public keys URI                   | `http://keycloak:8081/realms/comics-realm/protocol/openid-connect/certs` |
| `JWT_CONNECT_TIMEOUT`    | Keycloak connection timeout (default `2s`) | `5s`                                                                     |
| `JWT_READ_TIMEOUT`       | Keycloak read timeout (default `2s`)       | `5s`                                                                     |
| `MANAGEMENT_SERVER_PORT` | Spring Actuator port (default `8081`)      | `8081`                                                                   |

---

## OpenAPI / Scalar UI

API documentation is auto-generated via `SpringDoc OpenAPI` after application startup and available at the following
URLs (when running on `localhost:8080` in dev profile):

- **Scalar UI** (recommended): [`http://localhost:8080/scalar/index.html`](http://localhost:8080/scalar/index.html)
- **Swagger UI**: [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [`http://localhost:8080/v3/api-docs`](http://localhost:8080/v3/api-docs)

For protected endpoints, you need to provide a `Bearer` token obtained from Keycloak:

```
Authorization: Bearer <JWT-token>
```

Use the Keycloak Token Endpoint to get a token:

```
POST http://localhost:8081/realms/comics-realm/protocol/openid-connect/token
```

---

## License

This project is licensed under [Apache License 2.0](LICENSE). You can freely use, modify, and distribute
this code according to the license terms.
