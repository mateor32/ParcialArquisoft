import React, { useState } from "react";

const API_BASE = import.meta.env.VITE_API_BASE || "http://localhost:8080/api";

const StateColors = {
  "Ingresado": "#3498db",
  "En Procesamiento": "#f39c12",
  "Despachado": "#9b59b6",
  "Entregado": "#27ae60",
};

export default function App() {
  const [form, setForm] = useState({
    nombreCliente: "",
    direccion: "",
    telefono: "",
    descripcion: "",
    total: "",
  });
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [messageType, setMessageType] = useState("success");
  const [searchId, setSearchId] = useState("");
  const [found, setFound] = useState(null);
  const [searchLoading, setSearchLoading] = useState(false);

  const validateForm = () => {
    if (!form.nombreCliente.trim()) return "Nombre requerido";
    if (!form.direccion.trim()) return "Dirección requerida";
    if (!/^\d{7,20}$/.test(form.telefono)) return "Teléfono debe tener 7-20 dígitos";
    if (!form.descripcion.trim()) return "Descripción requerida";
    if (Number(form.total) <= 0) return "Total debe ser mayor a 0";
    return null;
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    const error = validateForm();
    if (error) {
      setMessage(error);
      setMessageType("error");
      return;
    }
    
    setLoading(true);
    try {
      const res = await fetch(`${API_BASE}/pedidos`, {
        method: "POST",
        headers: {
          "Content-Type": "application/vnd.equipo4.v1+json",
          Accept: "application/vnd.equipo4.v1+json",
        },
        body: JSON.stringify({ ...form, total: Number(form.total) }),
      });
      if (!res.ok) throw new Error(`Error ${res.status}`);
      const data = await res.json();
      setMessage(`✓ Pedido creado exitosamente - ID: ${data.idPedido}`);
      setMessageType("success");
      setForm({ nombreCliente: "", direccion: "", telefono: "", descripcion: "", total: "" });
    } catch (err) {
      setMessage(`✗ Error: ${err.message}`);
      setMessageType("error");
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchId.trim()) {
      setMessage("Ingresa un ID");
      setMessageType("error");
      return;
    }
    
    setFound(null);
    setSearchLoading(true);
    try {
      const res = await fetch(`${API_BASE}/pedidos/${searchId}`, {
        headers: { Accept: "application/vnd.equipo4.v1+json" },
      });
      if (!res.ok) throw new Error(`No encontrado (${res.status})`);
      const data = await res.json();
      setFound(data);
      setMessage(null);
    } catch (err) {
      setMessage(`✗ ${err.message}`);
      setMessageType("error");
    } finally {
      setSearchLoading(false);
    }
  };

  return (
    <div className="app">
      <header className="header">
        <div className="header-content">
          <h1 className="title">📦 Gestión de Pedidos</h1>
          <p className="subtitle">Sistema de pedidos en tiempo real</p>
        </div>
      </header>

      <main className="container">
        <div className="grid">
          <section className="panel create-panel">
            <div className="panel-header">
              <h2>Crear Nuevo Pedido</h2>
              <span className="icon">✏️</span>
            </div>
            <form onSubmit={handleCreate} className="form">
              <div className="form-group">
                <label htmlFor="nombre">Nombre del Cliente</label>
                <input
                  id="nombre"
                  type="text"
                  placeholder="Juan Pérez"
                  value={form.nombreCliente}
                  onChange={(e) => setForm({ ...form, nombreCliente: e.target.value })}
                  disabled={loading}
                />
              </div>
              <div className="form-group">
                <label htmlFor="direccion">Dirección</label>
                <input
                  id="direccion"
                  type="text"
                  placeholder="Calle Principal 123"
                  value={form.direccion}
                  onChange={(e) => setForm({ ...form, direccion: e.target.value })}
                  disabled={loading}
                />
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="telefono">Teléfono</label>
                  <input
                    id="telefono"
                    type="tel"
                    placeholder="3001234567"
                    value={form.telefono}
                    onChange={(e) => setForm({ ...form, telefono: e.target.value })}
                    disabled={loading}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="total">Total ($)</label>
                  <input
                    id="total"
                    type="number"
                    step="0.01"
                    min="0"
                    placeholder="99.99"
                    value={form.total}
                    onChange={(e) => setForm({ ...form, total: e.target.value })}
                    disabled={loading}
                  />
                </div>
              </div>
              <div className="form-group">
                <label htmlFor="descripcion">Descripción</label>
                <input
                  id="descripcion"
                  type="text"
                  placeholder="Detalles del pedido..."
                  value={form.descripcion}
                  onChange={(e) => setForm({ ...form, descripcion: e.target.value })}
                  disabled={loading}
                />
              </div>
              <button type="submit" className="btn btn-primary" disabled={loading}>
                {loading ? "⏳ Creando..." : "✓ Crear Pedido"}
              </button>
            </form>
          </section>

          <section className="panel search-panel">
            <div className="panel-header">
              <h2>Consultar Pedido</h2>
              <span className="icon">🔍</span>
            </div>
            <form onSubmit={handleSearch} className="form">
              <div className="form-group">
                <label htmlFor="search-id">ID del Pedido</label>
                <input
                  id="search-id"
                  type="number"
                  placeholder="ej: 1"
                  value={searchId}
                  onChange={(e) => setSearchId(e.target.value)}
                  disabled={searchLoading}
                />
              </div>
              <button type="submit" className="btn btn-secondary" disabled={searchLoading}>
                {searchLoading ? "⏳ Buscando..." : "🔍 Buscar"}
              </button>
            </form>

            {found && (
              <div className="result-card">
                <div className="result-header">
                  <h3>Pedido #{found.idPedido}</h3>
                  <span className="badge" style={{ backgroundColor: StateColors[found.estado] || "#95a5a6" }}>
                    {found.estado}
                  </span>
                </div>
                <div className="result-body">
                  <div className="result-row">
                    <span className="label">👤 Cliente:</span>
                    <span className="value">{found.nombreCliente}</span>
                  </div>
                  <div className="result-row">
                    <span className="label">📍 Dirección:</span>
                    <span className="value">{found.direccion}</span>
                  </div>
                  <div className="result-row">
                    <span className="label">💰 Total:</span>
                    <span className="value">\${parseFloat(found.total).toFixed(2)}</span>
                  </div>
                  <div className="result-row">
                    <span className="label">📝 Descripción:</span>
                    <span className="value">{found.descripcion}</span>
                  </div>
                  <div className="result-row">
                    <span className="label">📅 Creado:</span>
                    <span className="value">{new Date(Array.isArray(found.fechaCreacion) ? found.fechaCreacion[0] + '-' + String(found.fechaCreacion[1]).padStart(2,'0') + '-' + String(found.fechaCreacion[2]).padStart(2,'0') : found.fechaCreacion).toLocaleDateString()}</span>
                  </div>
                </div>
              </div>
            )}
          </section>
        </div>

        {message && (
          <div className={`message message-${messageType}`}>
            {message}
          </div>
        )}
      </main>

      <footer className="footer">
        <p>Equipo 4 - Sistema de Gestión de Pedidos © 2026</p>
      </footer>
    </div>
  );
}
