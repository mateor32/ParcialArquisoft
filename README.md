# Pedidos API — Cómo ejecutar

Instrucciones rápidas para dejar todo funcionando en tu máquina.

Requisitos:

- Java 17
- Maven
- Node 18 + npm (solo si quieres ejecutar el frontend con Vite)
- (Opcional) Docker Desktop si quieres docker-compose

1. Ejecutar backend (H2 por defecto)

```bash
# desde la raíz del proyecto
mvn spring-boot:run
# o con wrapper
./mvnw spring-boot:run
```

El backend quedará disponible en `http://localhost:8080/api`.

2. Ejecutar frontend (desarrollo)

```bash
cd frontend
npm install
npm run dev
```

Abre `http://localhost:5173` para ver la UI. El frontend ya apunta por defecto a `http://localhost:8080/api`.

3. Docker (opcional)

Arranca Docker Desktop y luego:

```bash
docker compose up --build
```

El `docker-compose.yml` levanta `backend`, `frontend` y opcionalmente `postgres`. Para usar Postgres activa el profile `postgresql` en `SPRING_PROFILES_ACTIVE`.

Notas rápidas:

- Se añadió `CorsConfig` para permitir peticiones desde `http://localhost:5173`.
- Endpoints usan versionamiento por header: `application/vnd.equipo4.v1+json`. El backend acepta también `application/json`.

# Pedidos API - Spring Boot 3

API REST para la gestión de pedidos usando Spring Boot 3, Java 17 y Spring Data JPA.

## Stack Tecnológico

- **Java**: 17 LTS
- **Framework**: Spring Boot 3.3.0
- **Build**: Maven
- **Persistencia**: Spring Data JPA + H2 (desarrollo) / PostgreSQL (producción)
- **API Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Hypermedia**: Spring HATEOAS
- **Validación**: Spring Validation
- **Herramientas**: Lombok

## Estructura del Proyecto

```
src/main/java/com/equipo4/pedidosapi/
├── controller/      # Controladores REST
├── service/         # Lógica de negocio
├── repository/      # Acceso a datos
├── model/          # Entidades JPA
├── dto/            # Data Transfer Objects
├── exception/      # Excepciones personalizadas
└── config/         # Configuración de la aplicación
```

## Requisitos

- JDK 17+
- Maven 3.8+

## Instalación y Ejecución

### 1. Clonar el repositorio

```bash
cd ParcialArquisoft
```

### 2. Compilar el proyecto

```bash
mvn clean install
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080/api`

## Acceso a la Documentación

- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/api/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console (usuario: sa, sin contraseña)

## Próximos Pasos

Sigue los prompts en la guía para:

1. Crear las entidades (Pedido, Cliente, Producto, etc.)
2. Implementar repositorios y servicios
3. Crear controladores REST
4. Agregar validaciones y manejo de excepciones
