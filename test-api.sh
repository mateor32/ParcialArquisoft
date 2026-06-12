#!/bin/bash
# Script de Pruebas para la API de Pedidos - Equipo 4
# Ejecutar con: bash test-api.sh

BASE_URL="http://localhost:8080/api"
VERSION_HEADER="application/vnd.equipo4.v1+json"

echo "======================================"
echo "   API de Pedidos - Script de Pruebas"
echo "======================================"
echo ""

# ==================== TEST 1: CREAR PEDIDO ====================
echo "TEST 1: Crear un Pedido (POST /pedidos)"
echo "-------------------------------------------"

curl -X POST "$BASE_URL/pedidos" \
  -H "Content-Type: $VERSION_HEADER" \
  -H "Accept: $VERSION_HEADER" \
  -d '{
    "nombreCliente": "Juan Pérez",
    "direccion": "Calle 10 #5-20",
    "telefono": "3001234567",
    "descripcion": "1 Hamburguesa, 1 Gaseosa",
    "total": 25000
  }' \
  -w "\n\nStatus Code: %{http_code}\n\n" \
  -s | jq '.' 2>/dev/null || echo "Error parsing JSON"

echo ""
read -p "Presiona Enter para continuar con el siguiente test..."
echo ""

# ==================== TEST 2: OBTENER PEDIDO ====================
echo "TEST 2: Obtener Pedido por ID (GET /pedidos/1)"
echo "-------------------------------------------"

curl -X GET "$BASE_URL/pedidos/1" \
  -H "Accept: $VERSION_HEADER" \
  -w "\nStatus Code: %{http_code}\n\n" \
  -s | jq '.' 2>/dev/null || echo "Error parsing JSON"

echo ""
read -p "Presiona Enter para continuar con el siguiente test..."
echo ""

# ==================== TEST 3: OBTENER PEDIDO NO EXISTENTE ====================
echo "TEST 3: Intentar Obtener Pedido que No Existe (GET /pedidos/999)"
echo "-------------------------------------------"

curl -X GET "$BASE_URL/pedidos/999" \
  -H "Accept: $VERSION_HEADER" \
  -w "\nStatus Code: %{http_code}\n\n" \
  -s | jq '.' 2>/dev/null || echo "Error parsing JSON"

echo ""
echo "======================================"
echo "   Pruebas Completadas"
echo "======================================"
