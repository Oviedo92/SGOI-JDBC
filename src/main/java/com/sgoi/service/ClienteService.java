
package com.sgoi.service;

import com.sgoi.dao.ClienteDAO;
import com.sgoi.model.Cliente;

import java.util.List;
import java.util.Optional;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    // CREATE: registrar un cliente
    public Cliente registrar(Cliente cliente) {

        validarDatos(cliente);

        return clienteDAO.registrar(cliente);
    }

    // READ: listar todos los clientes
    public List<Cliente> listarTodos() {

        return clienteDAO.listarTodos();
    }

    // READ: buscar un cliente por ID
    public Optional<Cliente> buscarPorId(Long id) {

        validarId(id);

        return clienteDAO.buscarPorId(id);
    }

    // UPDATE: actualizar un cliente
    public boolean actualizar(Cliente cliente) {

        validarDatos(cliente);
        validarId(cliente.getId());

        return clienteDAO.actualizar(cliente);
    }

    // DELETE: eliminar un cliente
    public boolean eliminar(Long id) {

        validarId(id);

        return clienteDAO.eliminar(id);
    }

    // Validar los datos obligatorios y sus longitudes
    private void validarDatos(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "Los datos del cliente son obligatorios."
            );
        }

        if (cliente.getNombre() == null ||
                cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del cliente es obligatorio."
            );
        }

        if (cliente.getDocumento() == null ||
                cliente.getDocumento().isBlank()) {
            throw new IllegalArgumentException(
                    "El documento del cliente es obligatorio."
            );
        }

        if (cliente.getNombre().length() > 100) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar los 100 caracteres."
            );
        }

        if (cliente.getDocumento().length() > 30) {
            throw new IllegalArgumentException(
                    "El documento no puede superar los 30 caracteres."
            );
        }

        if (cliente.getTelefono() != null &&
                cliente.getTelefono().length() > 20) {
            throw new IllegalArgumentException(
                    "El teléfono no puede superar los 20 caracteres."
            );
        }

        if (cliente.getCorreo() != null &&
                cliente.getCorreo().length() > 120) {
            throw new IllegalArgumentException(
                    "El correo no puede superar los 120 caracteres."
            );
        }

        if (cliente.getDireccion() != null &&
                cliente.getDireccion().length() > 200) {
            throw new IllegalArgumentException(
                    "La dirección no puede superar los 200 caracteres."
            );
        }

        if (cliente.getCorreo() != null &&
                !cliente.getCorreo().isBlank() &&
                !cliente.getCorreo().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException(
                    "El formato del correo electrónico no es válido."
            );
        }
    }

    // Validar que el ID sea positivo
    private void validarId(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser un número positivo."
            );
        }
    }
}
