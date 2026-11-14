
package com.sgoi.dao;

import com.sgoi.config.DatabaseConnection;
import com.sgoi.exception.DataAccessException;
import com.sgoi.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO {

    private static final String INSERTAR = """
            INSERT INTO clientes (nombre, documento, telefono, correo, direccion)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id, fecha_registro
            """;

    private static final String LISTAR_TODOS = """
            SELECT id, nombre, documento, telefono, correo, direccion, fecha_registro
            FROM clientes
            ORDER BY id
            """;

    private static final String BUSCAR_POR_ID = """
            SELECT id, nombre, documento, telefono, correo, direccion, fecha_registro
            FROM clientes
            WHERE id = ?
            """;

    private static final String ACTUALIZAR = """
            UPDATE clientes
            SET nombre = ?, documento = ?, telefono = ?, correo = ?, direccion = ?
            WHERE id = ?
            """;

    private static final String ELIMINAR = """
            DELETE FROM clientes
            WHERE id = ?
            """;

    // CREATE: registrar un cliente
    public Cliente registrar(Cliente cliente) {

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERTAR)) {

            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getDocumento());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getCorreo());
            statement.setString(5, cliente.getDireccion());

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    cliente.setId(result.getLong("id"));

                    Timestamp fecha = result.getTimestamp("fecha_registro");

                    if (fecha != null) {
                        cliente.setFechaRegistro(fecha.toLocalDateTime());
                    }

                    return cliente;
                }
            }

            throw new DataAccessException(
                    "No fue posible recuperar el cliente registrado."
            );

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Error al registrar el cliente.", e
            );
        }
    }

    // READ: listar todos los clientes
    public List<Cliente> listarTodos() {

        List<Cliente> clientes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(LISTAR_TODOS);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                clientes.add(mapearCliente(result));
            }

            return clientes;

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Error al consultar los clientes.", e
            );
        }
    }

    // READ: buscar un cliente por su ID
    public Optional<Cliente> buscarPorId(Long id) {

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(BUSCAR_POR_ID)) {

            statement.setLong(1, id);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return Optional.of(mapearCliente(result));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Error al buscar el cliente con ID " + id, e
            );
        }
    }

    // UPDATE: actualizar un cliente
    public boolean actualizar(Cliente cliente) {

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ACTUALIZAR)) {

            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getDocumento());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getCorreo());
            statement.setString(5, cliente.getDireccion());
            statement.setLong(6, cliente.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Error al actualizar el cliente.", e
            );
        }
    }

    // DELETE: eliminar un cliente
    public boolean eliminar(Long id) {

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ELIMINAR)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Error al eliminar el cliente con ID " + id, e
            );
        }
    }

    // Convertir un registro SQL en un objeto Cliente
    private Cliente mapearCliente(ResultSet result) throws SQLException {

        Timestamp fecha = result.getTimestamp("fecha_registro");

        return new Cliente(
                result.getLong("id"),
                result.getString("nombre"),
                result.getString("documento"),
                result.getString("telefono"),
                result.getString("correo"),
                result.getString("direccion"),
                fecha != null ? fecha.toLocalDateTime() : null
        );
    }
}
