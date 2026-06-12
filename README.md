# 📦 Pedidos API - Gestión Integral de Pedidos

<div align="center">

[![Java](https://img.shields.io/badge/Java-17%20LTS-orange?logo=java&style=for-the-badge)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen?logo=spring&style=for-the-badge)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?logo=apache-maven&style=for-the-badge)](https://maven.apache.org/)
[![React](https://img.shields.io/badge/React-18.2.0-61DAFB?logo=react&style=for-the-badge)](https://react.dev/)
[![Docker](https://img.shields.io/badge/Docker-Supported-2496ED?logo=docker&style=for-the-badge)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

_Una solución moderna y escalable para la gestión de pedidos con arquitectura REST_

[Demo](#-inicio-rápido) • [Documentación](#-documentación) • [Características](#-características) • [Stack](#-stack-tecnológico) • [Contribuir](#-contribuir)

</div>

---

## 🚀 Características Principales

✨ _API REST Completa_ - Endpoints bien documentados con versionamiento por headers  
🔒 _CORS Configurado_ - Integración seamless entre frontend y backend  
📚 _Documentación Interactiva_ - Swagger UI integrado para explorar la API  
💾 _Persistencia Flexible_ - Soporte para H2 (desarrollo) y PostgreSQL (producción)  
🏗️ _Arquitectura Limpia_ - Separación clara de responsabilidades (MVC + Services)  
🐳 _Containerizado_ - Docker y Docker Compose para despliegue inmediato  
🎨 _Frontend Moderno_ - Interfaz React con Vite para desarrollo rápido  
⚡ _HATEOAS_ - Hipermedia como motor del estado de la aplicación  
✅ _Validación Robusta_ - Spring Validation con manejo de errores centralizado

---

## 🛠️ Stack Tecnológico

### Backend

| Tecnología          | Versión   | Propósito                       |
| :------------------ | :-------- | :------------------------------ |
| _Java_              | 17 LTS    | Lenguaje principal              |
| _Spring Boot_       | 3.3.0     | Framework web principal         |
| _Spring Data JPA_   | 3.3.0     | Acceso a datos y ORM            |
| _Spring HATEOAS_    | 3.3.0     | Hipermedia REST                 |
| _SpringDoc OpenAPI_ | 2.3.0     | Generar y servir Swagger UI     |
| _H2 Database_       | Latest    | Base de datos en memoria (dev)  |
| _PostgreSQL_        | 15-Alpine | Base de datos relacional (prod) |
| _Lombok_            | Latest    | Reducir boilerplate code        |

### Frontend

| Tecnología | Versión | Propósito               |
| :--------- | :------ | :---------------------- |
| _React_    | 18.2.0  | Framework UI            |
| _Vite_     | 4.3.9   | Build tool y dev server |
| _Node.js_  | 18+     | Runtime JavaScript      |

### DevOps

| Herramienta      | Propósito                       |
| :--------------- | :------------------------------ |
| _Maven_          | Gestión de dependencias y build |
| _Docker_         | Containerización                |
| _Docker Compose_ | Orquestación multi-contenedor   |

---

## 📋 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

bash
✓ Java Development Kit (JDK) 17 o superior
✓ Apache Maven 3.8+
✓ Node.js 18+ y npm (para el frontend)
✓ Docker Desktop (opcional, para ejecutar con contenedores)

### Verificar instalaciones:

bash
java -version # Debería mostrar Java 17+
mvn --version # Debería mostrar Maven 3.8+
node --version # Debería mostrar Node 18+
npm --version # Versión compatible con Node
docker --version # Opcional: Docker 20.10+

---

## ⚡ Inicio Rápido

### 🎯 Opción 1: Ejecución Local (Recomendado para Desarrollo)

#### Paso 1: Clonar o navegar al repositorio

bash
cd ParcialArquisoft

#### Paso 2: Ejecutar el Backend

bash

# Desde la raíz del proyecto

mvn clean install
mvn spring-boot:run

El backend estará disponible en:

- 🌐 _API_: http://localhost:8080/api
- 📚 _Swagger UI_: http://localhost:8080/api/swagger-ui.html
- 📊 _H2 Console_: http://localhost:8080/h2-console
  - Usuario: sa
  - Contraseña: (dejar en blanco)

#### Paso 3: Ejecutar el Frontend

En otra terminal:

bash
cd frontend
npm install
npm run dev

Abre tu navegador en: _http://localhost:5173_

---

### 🐳 Opción 2: Docker Compose (Recomendado para Producción)

bash

# Desde la raíz del proyecto

docker compose up --build

Accede a través de:

- 🎨 _Frontend_: http://localhost:5173
- 🌐 _Backend API_: http://localhost:8080/api
- 📚 _Documentación_: http://localhost:8080/api/swagger-ui.html
- 🐘 _PostgreSQL_: localhost:5432 (Usuario: pedidos, Contraseña: pedidos)

---

### 🐘 Opción 3: PostgreSQL en Desarrollo

Para usar PostgreSQL en lugar de H2:

bash

# Crear archivo .env o editar propiedades

export SPRING_PROFILES_ACTIVE=postgresql

# Luego ejecutar

mvn spring-boot:run

Asegúrate de que PostgreSQL esté corriendo con las credenciales correctas en application-postgresql.yml.

---

## 📁 Estructura del Proyecto

ParcialArquisoft/
├── src/main/java/com/equipo4/pedidosapi/
│ ├── PedidosApiApplication.java # 🚀 Punto de entrada
│ ├── controller/
│ │ └── PedidoController.java # 🎮 Endpoints REST
│ ├── service/
│ │ ├── PedidoService.java # 📋 Interfaz del servicio
│ │ └── PedidoServiceImpl.java # 💼 Implementación de lógica
│ ├── repository/
│ │ ├── PedidoRepository.java # 📊 Acceso a Pedidos
│ │ └── ClienteRepository.java # 📊 Acceso a Clientes
│ ├── model/
│ │ ├── Pedido.java # 📦 Entidad Pedido
│ │ ├── Cliente.java # 👤 Entidad Cliente
│ │ └── EstadoPedido.java # 🔄 Enumeración de estados
│ ├── dto/
│ │ ├── PedidoRequestDTO.java # 📥 DTO para solicitudes
│ │ ├── PedidoResponseDTO.java # 📤 DTO para respuestas
│ │ └── PedidoMapper.java # 🔄 Mapeador DTO-Entity
│ ├── exception/
│ │ ├── GlobalExceptionHandler.java # ⚠️ Manejo global de errores
│ │ ├── ErrorResponse.java # 📋 Estructura de error
│ │ └── PedidoNotFoundException.java # ❌ Excepciones personalizadas
│ └── config/
│ ├── CorsConfig.java # 🔐 Configuración CORS
│ └── OpenApiConfig.java # 📚 Configuración Swagger
│
├── src/main/resources/
│ ├── application.yml # ⚙️ Configuración por defecto (H2)
│ └── application-postgresql.yml # ⚙️ Configuración PostgreSQL
│
├── frontend/ # 🎨 Aplicación React
│ ├── src/
│ │ ├── App.jsx
│ │ ├── main.jsx
│ │ └── styles.css
│ ├── index.html
│ ├── package.json
│ └── Dockerfile
│
├── docker-compose.yml # 🐳 Orquestación de contenedores
├── Dockerfile # 🐳 Imagen del backend
├── pom.xml # 📦 Configuración Maven
└── README.md # 📖 Este archivo

---

## 📚 Documentación de API

### Endpoints Principales

#### 1️⃣ Crear un Pedido

http
POST /api/pedidos
Content-Type: application/vnd.equipo4.v1+json

{
"numeroOrden": "ORD-001",
"clienteId": 1,
"estado": "PENDIENTE",
"total": 150.50
}

_Respuesta (201 Created):_
json
{
"id": 1,
"numeroOrden": "ORD-001",
"clienteId": 1,
"estado": "PENDIENTE",
"total": 150.50,
"\_links": {
"self": { "href": "http://localhost:8080/api/pedidos/1" },
"all": { "href": "http://localhost:8080/api/pedidos" }
}
}

#### 2️⃣ Obtener Todos los Pedidos

http
GET /api/pedidos
Accept: application/vnd.equipo4.v1+json

#### 3️⃣ Obtener un Pedido por ID

http
GET /api/pedidos/{id}
Accept: application/vnd.equipo4.v1+json

#### 4️⃣ Actualizar un Pedido

http
PUT /api/pedidos/{id}
Content-Type: application/vnd.equipo4.v1+json

{
"estado": "COMPLETADO",
"total": 160.00
}

#### 5️⃣ Eliminar un Pedido

http
DELETE /api/pedidos/{id}

### 📖 Explora en Swagger UI

Accede a _http://localhost:8080/api/swagger-ui.html_ para una documentación interactiva completa donde puedes:

- Ver todos los endpoints
- Probar requests en vivo
- Ver esquemas de datos
- Consultar códigos de respuesta

---

## ⚙️ Configuración

### Archivo application.yml (H2 - Por Defecto)

yaml
server:
port: 8080
servlet:
context-path: /api

spring:
application:
name: pedidos-api
datasource:
url: jdbc:h2:mem:testdb
driverClassName: org.h2.Driver
username: sa
password:
h2:
console:
enabled: true
jpa:
database-platform: org.hibernate.dialect.H2Dialect
hibernate:
ddl-auto: create-drop
show-sql: true

### Archivo application-postgresql.yml (PostgreSQL)

yaml
spring:
datasource:
url: jdbc:postgresql://localhost:5432/pedidosdb
username: pedidos
password: pedidos
driverClassName: org.postgresql.Driver
jpa:
database-platform: org.hibernate.dialect.PostgreSQL10Dialect
hibernate:
ddl-auto: update

### Activar PostgreSQL

bash
export SPRING_PROFILES_ACTIVE=postgresql
mvn spring-boot:run

---

## 🔒 CORS y Versionamiento de API

### Headers de Versionamiento

La API utiliza versionamiento por headers para mayor flexibilidad:

bash
curl -H "Accept: application/vnd.equipo4.v1+json" \
 http://localhost:8080/api/pedidos

### Configuración CORS

El backend está configurado para aceptar peticiones desde http://localhost:5173 (frontend por defecto). Ver CorsConfig.java para modificar.

---

## 🧪 Testing

### Ejecutar Tests

bash
mvn test

### Usar Postman

Se incluye una colección de Postman en el archivo:

Pedidos-API-Postman-Collection.postman_collection.json

Importa este archivo en Postman para tener todos los endpoints pre-configurados.

---

## 🚄 Mejoras en Performance

- _Lazy Loading_: Las entidades están configuradas con lazy loading
- _Índices_: Se recomienda agregar índices en campos de búsqueda frecuentes
- _Paginación_: Implementar paginación en listados grandes
- _Caché_: Spring Cache está disponible para datos frecuentemente accedidos

---

## 🤝 Contribuir

Las contribuciones son bienvenidas. Para cambios importantes:

1. _Fork_ el repositorio
2. _Crea una rama_ para tu feature (git checkout -b feature/AmazingFeature)
3. _Commit_ tus cambios (git commit -m 'Add some AmazingFeature')
4. _Push_ a la rama (git push origin feature/AmazingFeature)
5. _Abre un Pull Request_

### Estándares de Código

- Sigue las convenciones de Maven/Spring Boot
- Usa Lombok para reducir boilerplate
- Incluye tests para nuevas funcionalidades
- Documenta métodos públicos con JavaDoc

---

## 📝 Roadmap

- [ ] Autenticación JWT
- [ ] Paginación en endpoints
- [ ] Búsqueda avanzada
- [ ] Auditoría de cambios
- [ ] WebSockets para notificaciones en tiempo real
- [ ] Métricas con Micrometer
- [ ] Integración con eventos (Spring Events)
- [ ] Caché distribuido (Redis)

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo LICENSE para más detalles.

---

## 📧 Contacto y Soporte

Para reportar bugs o solicitar features, abre un issue en el repositorio.

---

## 🎓 Recursos Útiles

- [Spring Boot Official Guide](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Swagger/OpenAPI Specification](https://swagger.io/specification/)
- [React Documentation](https://react.dev/)
- [Vite Guide](https://vitejs.dev/guide/)

---

<div align="center">

_Hecho con ❤️ por el Equipo 4_

[⬆️ Volver al inicio](#-pedidos-api---gestión-integral-de-pedidos)

</div>
