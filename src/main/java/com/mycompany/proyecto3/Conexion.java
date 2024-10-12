package com.mycompany.proyecto3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection mysqlConnection;
    private Connection postgresConnection;

    // Configuración de conexión MySQL
    private final String mysqlUrl = "jdbc:mysql://localhost:3306/Proyecto3";
    private final String mysqlUser = "root";
    private final String mysqlPassword = "JS##20!!20";

    // Configuración de conexión PostgreSQL
    private final String postgresUrl = "jdbc:postgresql://localhost:5432/Proyecto3";
    private final String postgresUser = "postgres";
    private final String postgresPassword = "JS##20!!20";

    public Conexion() {
        try {
            // Conectar a MySQL
            mysqlConnection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
            System.out.println("Conexión a MySQL exitosa.");
            
            // Conectar a PostgreSQL
            postgresConnection = DriverManager.getConnection(postgresUrl, postgresUser, postgresPassword);
            System.out.println("Conexión a PostgreSQL exitosa.");
            
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
    }

    // Obtener la conexión MySQL
    public Connection getMySQLConnection() {
        return mysqlConnection;
    }

    // Obtener la conexión PostgreSQL
    public Connection getPostgresConnection() {
        return postgresConnection;
    }

    // Cerrar ambas conexiones
    public void closeConnections() {
        try {
            if (mysqlConnection != null && !mysqlConnection.isClosed()) {
                mysqlConnection.close();
                System.out.println("Conexión MySQL cerrada.");
            }
            if (postgresConnection != null && !postgresConnection.isClosed()) {
                postgresConnection.close();
                System.out.println("Conexión PostgreSQL cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error cerrando conexiones: " + e.getMessage());
        }
    }
}
