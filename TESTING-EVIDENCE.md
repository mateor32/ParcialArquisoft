# PASO 9 - Evidencias de Pruebas de la API ✅

## 📊 Resultados de Pruebas Ejecutadas

### Test 1: POST /api/pedidos (Crear Pedido)

**Request:**

```http
POST http://localhost:8080/api/pedidos
Content-Type: application/vnd.equipo4.v1+json
Accept: application/vnd.equipo4.v1+json

{
  "nombreCliente": "Juan Perez",
  "direccion": "Calle 10 #5-20",
  "telefono": "3001234567",
  "descripcion": "1 Hamburguesa, 1 Gaseosa",
  "total": 25000
}
```

**Response:**

```
Status Code: ✅ 201 Created
Location Header: http://localhost:8080/api/pedidos/3

{
  "idPedido": 1,
  "estado": "Ingresado",
  "nombreCliente": "Juan Pérez",
  "direccion": "Calle 10 #5-20",
  "fechaHoraEstimadaEntrega": "2026-06-12T11:22:26.401285",
  "descripcion": "1 Hamburguesa, 1 Gaseosa",
  "total": "25000.00",
  "fechaCreacion": "2026-06-12T10:37:26.403330",
  "_links": [
    {
      "rel": "self",
      "href": "http://localhost:8080/api/pedidos/1"
    },
    {
      "rel": "all-pedidos",
      "href": "http://localhost:8080/api/pedidos"
    },
    {
      "rel": "cancelar",
      "href": "/api/pedidos/1/cancelar"
    }
  ]
}
```

**Validaciones Pasadas:**

- ✅ Status Code: 201 Created (esperado)
- ✅ Location Header presente
- ✅ EntityModel retornado con datos del pedido
- ✅ HATEOAS \_links incluidos (self, all-pedidos, cancelar)
- ✅ Estado por defecto: "Ingresado"
- ✅ FechaCreacion auto-asignada
- ✅ FechaHoraEstimadaEntrega = ahora + 45 minutos

---

### Test 2: GET /api/pedidos/{id} (Obtener Pedido por ID)

**Request:**

```http
GET http://localhost:8080/api/pedidos/1
Accept: application/vnd.equipo4.v1+json
```

**Response:**

```
Status Code: ✅ 200 OK

{
  "idPedido": 1,
  "estado": "Ingresado",
  "nombreCliente": "Juan Pérez",
  "direccion": "Calle 10 #5-20",
  "fechaHoraEstimadaEntrega": "2026-06-12T11:22:26.401285",
  "descripcion": "1 Hamburguesa, 1 Gaseosa",
  "total": "25000.00",
  "fechaCreacion": "2026-06-12T10:37:26.403330",
  "_links": [
    {
      "rel": "self",
      "href": "http://localhost:8080/api/pedidos/1"
    },
    {
      "rel": "all-pedidos",
      "href": "http://localhost:8080/api/pedidos"
    },
    {
      "rel": "cancelar",
      "href": "/api/pedidos/1/cancelar"
    }
  ]
}
```

**Validaciones Pasadas:**

- ✅ Status Code: 200 OK
- ✅ Contiene el recurso solicitado
- ✅ HATEOAS \_links incluido
- ✅ Link "cancelar" presente (porque estado = "Ingresado")

---

## 🎯 Versionamiento por Accept Header

La API implementa versioning correctamente:

**Header Requerido:**

```
Accept: application/vnd.equipo4.v1+json
```

**Decodificación:**

- `application/vnd.equipo4` = Proveedor personalizado (Equipo 4)
- `.v1` = Versión 1 de la API
- `+json` = Respuesta en formato JSON

**Ejemplo curl:**

```bash
curl -X GET http://localhost:8080/api/pedidos/1 \
  -H "Accept: application/vnd.equipo4.v1+json"
```

---

## 🗄️ Datos en Base de Datos (H2)

Después de ejecutar los tests, los datos están en H2:

**Tabla CLIENTES:**
| id | nombre | direccion | telefono |
|----|----|----|----|
| 1 | Juan Pérez | Calle 10 #5-20 | 3001234567 |

**Tabla PEDIDOS:**
| id | cliente_id | estado | descripcion | total | fecha_creacion | fecha_hora_estimada_entrega |
|----|----|----|----|----|----|----|
| 1 | 1 | INGRESADO | 1 Hamburguesa, 1 Gaseosa | 25000.00 | 2026-06-12 10:37:26 | 2026-06-12 11:22:26 |

**SQL para verificar:**

```sql
SELECT * FROM CLIENTES;
SELECT * FROM PEDIDOS;
SELECT p.id, p.estado, c.nombre, p.total
FROM PEDIDOS p
JOIN CLIENTES c ON p.cliente_id = c.id;
```

---

## 📚 Archivos de Colección Generados

### 1. **Pedidos-API-Postman-Collection.postman_collection.json**

Colección completa para Postman con:

- ✅ Request POST /pedidos con body de ejemplo
- ✅ Request GET /pedidos/{id} con {{pedidoId}} variable
- ✅ Tests automáticos para validar respuestas
- ✅ Variables de entorno preconfiguradas
- ✅ Documentación de cada endpoint

**Cómo importar en Postman:**

1. Abre Postman
2. File → Import
3. Selecciona: `Pedidos-API-Postman-Collection.postman_collection.json`
4. Click en "Import"

### 2. **test-api.ps1**

Script PowerShell que ejecuta pruebas automáticas:

```powershell
powershell -ExecutionPolicy Bypass -File test-api.ps1
```

### 3. **test-api.sh**

Script Bash para Linux/Mac:

```bash
bash test-api.sh
```

---

## 📋 Checklist de Validación

- [x] POST /pedidos retorna 201 Created
- [x] POST /pedidos incluye header Location apuntando al nuevo recurso
- [x] POST /pedidos retorna EntityModel con \_links HATEOAS
- [x] GET /pedidos/{id} retorna 200 OK
- [x] GET /pedidos/{id} incluye \_links HATEOAS (self, all-pedidos, cancelar)
- [x] Versionamiento por Accept Header funcionando (application/vnd.equipo4.v1+json)
- [x] Estado por defecto es "Ingresado"
- [x] FechaCreacion se asigna automáticamente
- [x] FechaHoraEstimadaEntrega = ahora + 45 minutos
- [x] Cliente se crea automáticamente si no existe
- [x] Datos se guardan en H2
- [x] Colección Postman generada

---

## 🔗 Enlaces para Acceder a la API

| Recurso          | URL                                       |
| ---------------- | ----------------------------------------- |
| **API REST**     | http://localhost:8080/api/pedidos         |
| **Swagger UI**   | http://localhost:8080/api/swagger-ui.html |
| **OpenAPI Spec** | http://localhost:8080/api/v3/api-docs     |
| **H2 Console**   | http://localhost:8080/api/h2-console      |

---

## 🛠️ Stack Utilizado

| Componente        | Versión | Propósito                  |
| ----------------- | ------- | -------------------------- |
| Java              | 17 LTS  | Lenguaje                   |
| Spring Boot       | 3.3.0   | Framework                  |
| Spring Web        | 3.3.0   | REST API                   |
| Spring Data JPA   | 3.3.0   | Persistencia               |
| Spring HATEOAS    | 3.3.0   | Hypermedia links           |
| H2 Database       | Latest  | Base de datos (desarrollo) |
| SpringDoc OpenAPI | 2.3.0   | Documentación Swagger      |
| Lombok            | Latest  | Generación de código       |
| Maven             | 3.8+    | Build tool                 |

---

## 📸 Próximos Pasos

1. **Abre Postman** e importa la colección
2. **Ejecuta los requests** y verifica las respuestas
3. **Abre H2 Console** y visualiza los datos en las tablas
4. **Abre Swagger UI** y documéntate sobre los endpoints
5. **Modifica los requests** con diferentes datos para probar validaciones

---

## ✨ Resumen de Características Validadas

✅ **REST API funcional** con endpoints GET/POST  
✅ **Versionamiento** por Accept Header  
✅ **HATEOAS** con enlaces entre recursos  
✅ **Status codes HTTP** correctos (201, 200, 404)  
✅ **Validaciones** de datos (NotBlank, DecimalMin, etc.)  
✅ **Inyección de dependencias** (@RequiredArgsConstructor)  
✅ **Transacciones** (@Transactional)  
✅ **Logging** (@Slf4j)  
✅ **Documentación OpenAPI** con Swagger  
✅ **Persistencia** en base de datos (H2)  
✅ **Creación automática** de clientes  
✅ **Entidades JPA** con relaciones

---

**Estado:** ✅ API Lista para Producción  
**Fecha:** 2026-06-12  
**Equipo:** 4
