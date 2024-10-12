package com.mycompany.proyecto3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class DatabaseSync {

    private final Conexion conexion;

    public DatabaseSync() {
        // Instanciar la clase Conexion para gestionar las conexiones
        conexion = new Conexion();
    }

    // Método para realizar una actualización de manera transaccional en ambas bases de datos
    public void actualizarRegistro(String dpi, String nuevoNombre) {
        Connection connMySQL = null;
        Connection connPostgres = null;
        Savepoint savepointMySQL = null;
        Savepoint savepointPostgres = null;

        try {
            // Obtener las conexiones de la clase Conexion
            connMySQL = conexion.getMySQLConnection();
            connPostgres = conexion.getPostgresConnection();

            // Desactivar auto-commit para manejar las transacciones manualmente
            connMySQL.setAutoCommit(false);
            connPostgres.setAutoCommit(false);

            // Actualizar en MySQL
            savepointMySQL = realizarActualizacion(connMySQL, dpi, nuevoNombre, "mysql");

            // Actualizar en PostgreSQL
            savepointPostgres = realizarActualizacion(connPostgres, dpi, nuevoNombre, "postgresql");

            // Si ambas actualizaciones fueron exitosas, commit en ambas bases de datos
            connMySQL.commit();
            connPostgres.commit();

            System.out.println("Actualización exitosa en ambas bases de datos.");

            // Registrar la transacción en un archivo de texto
            registrarTransaccion("UPDATE", dpi, nuevoNombre, "mysql");
            registrarTransaccion("UPDATE", dpi, nuevoNombre, "postgresql");

        } catch (SQLException e) {
            // Si hay un error, hacer rollback en ambas bases de datos
            try {
                if (connMySQL != null && savepointMySQL != null) {
                    connMySQL.rollback(savepointMySQL);
                }
                if (connPostgres != null && savepointPostgres != null) {
                    connPostgres.rollback(savepointPostgres);
                }
                System.out.println("Actualización fallida, se ha hecho rollback en ambas bases de datos.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // No cerrar conexiones aquí, ya que las maneja la clase Conexion
        }
    }

    // Método auxiliar para realizar la actualización en una base de datos
    private Savepoint realizarActualizacion(Connection conn, String dpi, String nuevoNombre, String dbType) throws SQLException {
        String query = "UPDATE empleados SET primer_nombre = ? WHERE dpi = ?";
        Savepoint savepoint = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, dpi);

            // Crear un savepoint antes de la ejecución de la actualización
            savepoint = conn.setSavepoint("Savepoint_" + dbType);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registro actualizado en " + dbType);
            } else {
                System.out.println("No se encontró el registro con el DPI: " + dpi + " en " + dbType);
                throw new SQLException("No se encontró el registro");
            }
        }

        return savepoint;
    }

    // Método para registrar la transacción en un archivo de texto
    private void registrarTransaccion(String tipo, String dpi, String nuevoNombre, String dbType) {
        try (FileWriter writer = new FileWriter("transacciones.log", true)) {
            writer.write(LocalDateTime.now() + " - " + tipo + " - DPI: " + dpi + " - Nombre: " + nuevoNombre + " - DB: " + dbType + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
}
