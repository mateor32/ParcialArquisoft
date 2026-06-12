# Guía de Pruebas - API de Pedidos (Equipo 4)

## 📋 Archivos Incluidos

### 1. **Pedidos-API-Postman-Collection.postman_collection.json**

Colección de Postman lista para importar con todos los endpoints y tests automáticos.

### 2. **test-api.ps1**

Script de PowerShell que ejecuta todas las pruebas con curl/Invoke-WebRequest.

---

## 🚀 Opción 1: Usar Postman (Recomendado para ver detalles)

### Paso 1: Descargar e Instalar Postman

Descarga desde: https://www.postman.com/downloads/

### Paso 2: Importar la Colección

1. Abre Postman
2. Haz clic en **Import** (arriba izquierda)
3. Selecciona el archivo: `Pedidos-API-Postman-Collection.postman_collection.json`
4. Haz clic en **Import**

### Paso 3: Ver Variables de Entorno

- Dentro de Postman verás la colección con carpetas:
  - **Pedidos** (Crear, Obtener)
  - **Documentación** (Swagger, OpenAPI, H2)

- Variables automáticas:
  - `{{baseUrl}}` = `http://localhost:8080/api`
  - `{{pedidoId}}` = Se asigna automáticamente después de crear un pedido

### Paso 4: Ejecutar Requests

**CREAR PEDIDO (POST):**

1. Abre **Pedidos** → **Crear Pedido (POST)**
2. Modifica el body JSON si deseas (opcional)
3. Haz clic en **Send**
4. Verás:
   - ✅ Status: **201 Created**
   - ✅ Header: **Location: http://localhost:8080/api/pedidos/1**
   - ✅ Body con \_links HATEOAS

**OBTENER PEDIDO (GET):**

1. Abre **Pedidos** → **Obtener Pedido por ID (GET)**
2. Automáticamente usa el `{{pedidoId}}` guardado
3. Haz clic en **Send**
4. Verás:
   - ✅ Status: **200 OK**
   - ✅ Body con detalles del pedido y \_links HATEOAS

---

## 💻 Opción 2: Ejecutar Script PowerShell

### Paso 1: Asegúrate que la app está corriendo

```bash
mvn spring-boot:run
```

### Paso 2: Abre PowerShell y navega a la carpeta del proyecto

```powershell
cd C:\Users\mater\Desktop\ParcialArquisoft
```

### Paso 3: Ejecuta el script

```powershell
powershell -ExecutionPolicy Bypass -File test-api.ps1
```

**Salida esperada:**

```
======================================
   API de Pedidos - Script de Pruebas
======================================

TEST 1: Crear un Pedido (POST /pedidos)
-------------------------------------------
Request:
POST http://localhost:8080/api/pedidos
Headers:
  Content-Type: application/vnd.equipo4.v1+json
  Accept: application/vnd.equipo4.v1+json

Response:
Status Code: 201
Location Header: http://localhost:8080/api/pedidos/1
Body:
{
  "idPedido": 1,
  "estado": "Ingresado",
  "nombreCliente": "Juan Pérez",
  ...
  "_links": {
    "self": {...},
    "all-pedidos": {...}
  }
}

ID del Pedido Creado: 1

TEST 2: Obtener Pedido por ID (GET /pedidos/{id})
-------------------------------------------
...
```

---

## 📝 Ejemplos de Requests con Curl

### POST - Crear Pedido

```bash
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/vnd.equipo4.v1+json" \
  -H "Accept: application/vnd.equipo4.v1+json" \
  -d '{
    "nombreCliente": "Juan Pérez",
    "direccion": "Calle 10 #5-20",
    "telefono": "3001234567",
    "descripcion": "1 Hamburguesa, 1 Gaseosa",
    "total": 25000
  }' \
  -w "\nStatus: %{http_code}\n"
```

**Respuesta esperada:**

```
Status: 201
Location: http://localhost:8080/api/pedidos/1

{
  "idPedido": 1,
  "estado": "Ingresado",
  "nombreCliente": "Juan Pérez",
  "direccion": "Calle 10 #5-20",
  "fechaHoraEstimadaEntrega": "2026-06-12T15:30:45.123456",
  "descripcion": "1 Hamburguesa, 1 Gaseosa",
  "total": "25000",
  "fechaCreacion": "2026-06-12T14:45:45.123456",
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/pedidos/1"
    },
    "all-pedidos": {
      "href": "http://localhost:8080/api/pedidos"
    }
  }
}
```

### GET - Obtener Pedido por ID

```bash
curl -X GET http://localhost:8080/api/pedidos/1 \
  -H "Accept: application/vnd.equipo4.v1+json" \
  -w "\nStatus: %{http_code}\n"
```

**Respuesta esperada:**

```
Status: 200

{
  "idPedido": 1,
  "estado": "Ingresado",
  "nombreCliente": "Juan Pérez",
  "direccion": "Calle 10 #5-20",
  "fechaHoraEstimadaEntrega": "2026-06-12T15:30:45.123456",
  "descripcion": "1 Hamburguesa, 1 Gaseosa",
  "total": "25000",
  "fechaCreacion": "2026-06-12T14:45:45.123456",
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/pedidos/1"
    },
    "all-pedidos": {
      "href": "http://localhost:8080/api/pedidos"
    },
    "cancelar": {
      "href": "/api/pedidos/1/cancelar"
    }
  }
}
```

### GET - Pedido No Encontrado (404)

```bash
curl -X GET http://localhost:8080/api/pedidos/999 \
  -H "Accept: application/vnd.equipo4.v1+json" \
  -w "\nStatus: %{http_code}\n"
```

**Respuesta esperada:**

```
Status: 404

{
  "status": 404,
  "timestamp": "2026-06-12T14:45:45",
  "mensaje": "Pedido no encontrado con ID: 999",
  "detalle": "El recurso solicitado no existe",
  "timestamp_response": "2026-06-12T14:45:45"
}
```

---

## 🔗 Versionamiento por Accept Header

La API implementa versionamiento usando el header **Accept**:

```
Accept: application/vnd.equipo4.v1+json
```

**Esto significa:**

- `application/vnd.equipo4` = Proveedor personalizado
- `v1` = Versión 1
- `+json` = Formato JSON

**En Postman:**

1. Ve a **Headers**
2. Agrega: `Accept: application/vnd.equipo4.v1+json`

**En Curl:**

```bash
-H "Accept: application/vnd.equipo4.v1+json"
```

---

## 🗄️ Ver Datos en la Base de Datos

Después de crear pedidos, puedes verlos en:

### H2 Console (Desarrollo)

1. Abre: http://localhost:8080/api/h2-console
2. Click en **Connect**
3. En el lado izquierdo verás:
   - `CLIENTES` (tabla de clientes)
   - `PEDIDOS` (tabla de pedidos)
4. Escribe queries como:
   ```sql
   SELECT * FROM CLIENTES;
   SELECT * FROM PEDIDOS;
   SELECT p.*, c.nombre FROM PEDIDOS p
   JOIN CLIENTES c ON p.cliente_id = c.id;
   ```

---

## ✅ Checklist de Validación

- [ ] POST /pedidos retorna 201 Created
- [ ] POST /pedidos incluye header Location
- [ ] POST /pedidos retorna \_links HATEOAS
- [ ] GET /pedidos/{id} retorna 200 OK
- [ ] GET /pedidos/{id} incluye \_links HATEOAS (self, all-pedidos, cancelar)
- [ ] GET /pedidos/999 retorna 404 Not Found
- [ ] Los datos aparecen en H2 Console
- [ ] Swagger documenta ambos endpoints

---

## 📚 Documentación Interactiva

- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v3/api-docs

En Swagger puedes:

1. Ver la documentación completa
2. Probar los endpoints directamente
3. Ver ejemplos de request/response
4. Ver los modelos de datos

---

## 🐛 Troubleshooting

### Error: Connection refused

- Verifica que la app esté corriendo: `mvn spring-boot:run`
- Verifica el puerto 8080 esté disponible

### Error: Accept header not recognized

- Asegúrate de usar exactamente: `application/vnd.equipo4.v1+json`

### Error: 400 Bad Request en POST

- Verifica que el JSON sea válido
- Verifica que todos los campos requeridos estén presentes
- Verifica el formato de los tipos (total debe ser número)

### Error: 404 Not Found

- Verifica que el ID del pedido exista
- Recuerda que los datos en H2 se pierden al reiniciar (ddl-auto=create-drop)

---

## 🎯 Resumen

| Tarea                      | Herramienta  | Comando                                   |
| -------------------------- | ------------ | ----------------------------------------- |
| Probar con interfaz visual | Postman      | Importar .json                            |
| Probar automático          | PowerShell   | `powershell -File test-api.ps1`           |
| Probar manual              | Curl/Browser | `curl -X GET ...`                         |
| Ver BD                     | H2 Console   | http://localhost:8080/api/h2-console      |
| Ver documentación          | Swagger      | http://localhost:8080/api/swagger-ui.html |
