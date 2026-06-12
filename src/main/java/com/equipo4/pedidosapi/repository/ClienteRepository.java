package com.equipo4.pedidosapi.repository;

import com.equipo4.pedidosapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Cliente.
 * Proporciona operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca un cliente por nombre exacto.
     * 
     * @param nombre el nombre del cliente
     * @return Optional con el cliente si existe
     */
    Optional<Cliente> findByNombre(String nombre);

    /**
     * Busca un cliente por teléfono.
     * 
     * @param telefono el teléfono del cliente
     * @return Optional con el cliente si existe
     */
    Optional<Cliente> findByTelefono(String telefono);
}
