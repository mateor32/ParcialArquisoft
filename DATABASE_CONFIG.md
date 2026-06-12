# Guía de Configuración de Base de Datos

## 📊 Configuración Actual: H2 en Memoria

La aplicación está configurada por defecto para usar **H2 en memoria** con el nombre `pedidosdb`.

### Características de H2:

✅ No requiere instalación adicional
✅ Ideal para desarrollo y pruebas
✅ Los datos se pierden cuando se reinicia la aplicación (ddl-auto=create-drop)
✅ Consola web en: http://localhost:8080/api/h2-console

### Acceso a H2 Console:

1. Inicia la aplicación: `mvn spring-boot:run`
2. Abre el navegador: **http://localhost:8080/api/h2-console**
3. Rellena los campos:
   - **Driver Class**: `org.h2.Driver`
   - **JDBC URL**: `jdbc:h2:mem:pedidosdb`
   - **User Name**: `sa`
   - **Password**: (dejar vacío)
4. Haz clic en "Connect"

---

## 🐘 Cambiar a PostgreSQL

### Requisitos Previos:

1. **Instalar PostgreSQL** en tu máquina local
2. **Crear la base de datos**:
   ```sql
   CREATE DATABASE pedidosdb;
   ```

### Pasos para Cambiar a PostgreSQL:

#### 1. Actualizar Credenciales en `application-postgresql.yml`

Abre el archivo `src/main/resources/application-postgresql.yml` y cambia:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pedidosdb
    driverClassName: org.postgresql.Driver
    username: postgres
    password: TU_CONTRASEÑA_AQUI # Reemplaza con tu contraseña PostgreSQL
```

#### 2. Ejecutar con el Perfil PostgreSQL

**Opción A: Línea de comandos**

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=postgresql"
```

**Opción B: Variable de entorno (PowerShell)**

```powershell
$env:SPRING_PROFILES_ACTIVE="postgresql"
mvn spring-boot:run
```

**Opción C: En el IDE (si usas IntelliJ)**

- Run → Edit Configurations
- En "VM options" o "Program arguments", agrega:
  ```
  --spring.profiles.active=postgresql
  ```

### Acceso a PostgreSQL:

Una vez iniciada la aplicación con PostgreSQL, puedes acceder de varias formas:

**Opción 1: pgAdmin (Interfaz Web)**

```
http://localhost:5050
```

(Si tienes pgAdmin instalado)

**Opción 2: psql (Línea de comandos)**

```bash
psql -U postgres -d pedidosdb
```

**Opción 3: DBeaver (Descargable Gratis)**

- Descarga desde: https://dbeaver.io/download/
- Crea una conexión:
  - Host: `localhost`
  - Puerto: `5432`
  - Base de datos: `pedidosdb`
  - Usuario: `postgres`
  - Contraseña: (tu contraseña)

---

## 📝 Configuración de Archivos

### `application.yml` (Por Defecto - H2)

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:pedidosdb
    driverClassName: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop # Recrea tablas al iniciar
    show-sql: true # Muestra SQL en consola
  h2:
    console:
      enabled: true
      path: /h2-console
      settings:
        web-allow-others: true
```

### `application-postgresql.yml` (PostgreSQL)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pedidosdb
    driverClassName: org.postgresql.Driver
    username: postgres
    password: password # CAMBIAR CON TU CONTRASEÑA
  jpa:
    hibernate:
      ddl-auto: update # Actualiza schema sin perder datos
      dialect: org.hibernate.dialect.PostgreSQLDialect
```

---

## 🔄 Cambiar Entre H2 y PostgreSQL

**Para H2:**

```bash
mvn spring-boot:run
# O sin perfil explícito (H2 es el default)
```

**Para PostgreSQL:**

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=postgresql"
```

---

## ⚙️ Configuración de Spring JPA

| Propiedad    | H2                                | PostgreSQL                                |
| ------------ | --------------------------------- | ----------------------------------------- |
| `ddl-auto`   | `create-drop`                     | `update`                                  |
| `dialect`    | `org.hibernate.dialect.H2Dialect` | `org.hibernate.dialect.PostgreSQLDialect` |
| `show-sql`   | `true`                            | `true`                                    |
| `format_sql` | `true`                            | `true`                                    |

**Explicación:**

- `create-drop`: Crea las tablas al iniciar y las borra al detener (ideal para testing)
- `update`: Crea/actualiza tablas sin borrar datos (ideal para producción)

---

## 🐛 Troubleshooting

### Problema: "Conexión rechazada a PostgreSQL"

**Solución:**

```bash
# Verifica que PostgreSQL esté corriendo
pg_isready -h localhost -p 5432

# O inicia PostgreSQL (si está parado)
# En Windows: net start postgresql-x64-VERSION
# En Linux: sudo systemctl start postgresql
```

### Problema: "Usuario o contraseña incorrectos"

**Solución:**

```bash
# Verifica con psql directamente
psql -U postgres -h localhost
```

### Problema: "Base de datos no existe"

**Solución:**

```bash
# Crear la BD
psql -U postgres
CREATE DATABASE pedidosdb;
```

---

## 📌 Resumen Rápido

| Acción                 | Comando                                                                                 |
| ---------------------- | --------------------------------------------------------------------------------------- |
| Iniciar con H2         | `mvn spring-boot:run`                                                                   |
| Iniciar con PostgreSQL | `mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=postgresql"` |
| Acceder a H2 Console   | http://localhost:8080/api/h2-console                                                    |
| Acceder a pgAdmin      | http://localhost:5050                                                                   |
| Ver SQL en consola     | Ya habilitado (`show-sql: true`)                                                        |
