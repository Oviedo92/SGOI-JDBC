
package com.sgoi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = System.getenv("SGOI_DB_URL");
    private static final String USER = System.getenv("SGOI_DB_USER");
    private static final String PASSWORD = System.getenv("SGOI_DB_PASSWORD");

    private DatabaseConnection() {
        // Evita crear instancias de esta clase.
    }

    public static Connection getConnection() throws SQLException {

        if (URL == null || USER == null || PASSWORD == null) {
            throw new SQLException(
                "Faltan las variables de entorno de conexión a PostgreSQL."
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
