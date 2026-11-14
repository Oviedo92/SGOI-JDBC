
package com.sgoi;

import com.sgoi.config.DatabaseConnection;
import com.sgoi.ui.ClienteMenu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println(" INICIANDO SISTEMA SGOI");
        System.out.println("==================================");

        // 1. Verificar la conexión JDBC con PostgreSQL
        System.out.println("\nVerificando conexión con PostgreSQL...");

        try (
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                "SELECT current_database(), current_user"
            )
        ) {

            if (result.next()) {
                System.out.println("\nConexión exitosa con PostgreSQL.");
                System.out.println("Base de datos: " + result.getString(1));
                System.out.println("Usuario: " + result.getString(2));
            }

        } catch (Exception e) {

            System.err.println("\nError al conectar con PostgreSQL:");
            System.err.println(e.getMessage());

            System.err.println(
                "No se puede iniciar el módulo de Gestión de Clientes."
            );

            return;
        }

        // 2. Iniciar el menú del módulo de Gestión de Clientes
        System.out.println("\nIniciando módulo de Gestión de Clientes...");

        ClienteMenu menu = new ClienteMenu();
        menu.iniciar();
    }
}
