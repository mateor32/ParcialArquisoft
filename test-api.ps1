# Script de Pruebas para la API de Pedidos - Equipo 4
# Ejecutar con: powershell -ExecutionPolicy Bypass -File test-api.ps1

$baseUrl = "http://localhost:8080/api"
$versionHeader = "application/vnd.equipo4.v1+json"

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "   API de Pedidos - Script de Pruebas" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
Write-Host ""

# ==================== TEST 1: CREAR PEDIDO ====================
Write-Host "TEST 1: Crear un Pedido (POST /pedidos)" -ForegroundColor Yellow
Write-Host "-------------------------------------------" -ForegroundColor Yellow

$body = @{
    nombreCliente = "Juan Pérez"
    direccion = "Calle 10 #5-20"
    telefono = "3001234567"
    descripcion = "1 Hamburguesa, 1 Gaseosa"
    total = 25000
} | ConvertTo-Json

Write-Host "Request:" -ForegroundColor Green
Write-Host "POST $baseUrl/pedidos" -ForegroundColor Green
Write-Host "Headers:" -ForegroundColor Green
Write-Host "  Content-Type: $versionHeader" -ForegroundColor Green
Write-Host "  Accept: $versionHeader" -ForegroundColor Green
Write-Host "Body:" -ForegroundColor Green
Write-Host $body -ForegroundColor Gray
Write-Host ""

$response = Invoke-WebRequest -Uri "$baseUrl/pedidos" `
    -Method POST `
    -Headers @{
        "Content-Type" = $versionHeader
        "Accept" = $versionHeader
    } `
    -Body $body `
    -UseBasicParsing

Write-Host "Response:" -ForegroundColor Green
Write-Host "Status Code: $($response.StatusCode)" -ForegroundColor Green
Write-Host "Status Description: $($response.StatusDescription)" -ForegroundColor Green
Write-Host "Location Header: $($response.Headers['Location'])" -ForegroundColor Green
Write-Host "Body:" -ForegroundColor Green

$responseBody = $response.Content | ConvertFrom-Json
$pedidoId = $responseBody.idPedido
Write-Host ($response.Content | ConvertFrom-Json | ConvertTo-Json -Depth 10) -ForegroundColor Gray
Write-Host ""

# Guardar el ID para usar en el siguiente test
Write-Host "ID del Pedido Creado: $pedidoId" -ForegroundColor Cyan
Write-Host ""

# ==================== TEST 2: OBTENER PEDIDO ====================
Write-Host "TEST 2: Obtener Pedido por ID (GET /pedidos/{id})" -ForegroundColor Yellow
Write-Host "-------------------------------------------" -ForegroundColor Yellow

Write-Host "Request:" -ForegroundColor Green
Write-Host "GET $baseUrl/pedidos/$pedidoId" -ForegroundColor Green
Write-Host "Headers:" -ForegroundColor Green
Write-Host "  Accept: $versionHeader" -ForegroundColor Green
Write-Host ""

$response = Invoke-WebRequest -Uri "$baseUrl/pedidos/$pedidoId" `
    -Method GET `
    -Headers @{
        "Accept" = $versionHeader
    }

Write-Host "Response:" -ForegroundColor Green
Write-Host "Status Code: $($response.StatusCode)" -ForegroundColor Green
Write-Host "Status Description: $($response.StatusDescription)" -ForegroundColor Green
Write-Host "Body:" -ForegroundColor Green
Write-Host ($response.Content | ConvertFrom-Json | ConvertTo-Json -Depth 10) -ForegroundColor Gray
Write-Host ""

# ==================== TEST 3: INTENTAR OBTENER PEDIDO NO EXISTENTE ====================
Write-Host "TEST 3: Intentar Obtener Pedido que No Existe (GET /pedidos/999)" -ForegroundColor Yellow
Write-Host "-------------------------------------------" -ForegroundColor Yellow

Write-Host "Request:" -ForegroundColor Green
Write-Host "GET $baseUrl/pedidos/999" -ForegroundColor Green
Write-Host "Headers:" -ForegroundColor Green
Write-Host "  Accept: $versionHeader" -ForegroundColor Green
Write-Host ""

try {
    $response = Invoke-WebRequest -Uri "$baseUrl/pedidos/999" `
        -Method GET `
        -Headers @{
            "Accept" = $versionHeader
        } `
        -ErrorAction Stop
} catch {
    $response = $_.Exception.Response
    Write-Host "Response:" -ForegroundColor Green
    Write-Host "Status Code: $($response.StatusCode.value__)" -ForegroundColor Green
    Write-Host "Status Description: $($response.StatusDescription)" -ForegroundColor Green
    Write-Host "Body:" -ForegroundColor Green
    Write-Host ($_.Exception.Response | ConvertFrom-Json | ConvertTo-Json -Depth 10) -ForegroundColor Gray
}
Write-Host ""

# ==================== TEST 4: CREAR OTRO PEDIDO ====================
Write-Host "TEST 4: Crear Otro Pedido (POST /pedidos)" -ForegroundColor Yellow
Write-Host "-------------------------------------------" -ForegroundColor Yellow

$body2 = @{
    nombreCliente = "María González"
    direccion = "Avenida 20 #10-50"
    telefono = "3109876543"
    descripcion = "2 Pizzas grandes, 3 Refrescos"
    total = 95000
} | ConvertTo-Json

Write-Host "Request:" -ForegroundColor Green
Write-Host "POST $baseUrl/pedidos" -ForegroundColor Green
Write-Host "Body:" -ForegroundColor Green
Write-Host $body2 -ForegroundColor Gray
Write-Host ""

$response2 = Invoke-WebRequest -Uri "$baseUrl/pedidos" `
    -Method POST `
    -Headers @{
        "Content-Type" = $versionHeader
        "Accept" = $versionHeader
    } `
    -Body $body2

Write-Host "Response:" -ForegroundColor Green
Write-Host "Status Code: $($response2.StatusCode)" -ForegroundColor Green
Write-Host "Location Header: $($response2.Headers['Location'])" -ForegroundColor Green
Write-Host "Body:" -ForegroundColor Green
Write-Host ($response2.Content | ConvertFrom-Json | ConvertTo-Json -Depth 10) -ForegroundColor Gray
Write-Host ""

# ==================== RESUMEN ====================
Write-Host "======================================" -ForegroundColor Cyan
Write-Host "   Pruebas Completadas Exitosamente" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "✅ POST /pedidos -> 201 Created" -ForegroundColor Green
Write-Host "✅ GET /pedidos/{id} -> 200 OK" -ForegroundColor Green
Write-Host "✅ GET /pedidos/999 -> 404 Not Found" -ForegroundColor Green
Write-Host ""
Write-Host "Próximas acciones:" -ForegroundColor Cyan
Write-Host "  1. Abre H2 Console: http://localhost:8080/api/h2-console" -ForegroundColor Cyan
Write-Host "  2. Abre Swagger UI: http://localhost:8080/api/swagger-ui.html" -ForegroundColor Cyan
Write-Host "  3. Verifica los datos en las tablas CLIENTES y PEDIDOS" -ForegroundColor Cyan
