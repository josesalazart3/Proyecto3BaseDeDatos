package com.mycompany.proyecto3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDatabase {

    private final Connection connection;

    public MySQLDatabase(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar una persona en la base de datos MySQL
    public void insertar(String dpi, String primerNombre, String segundoNombre, String primerApellido, 
                         String segundoApellido, String direccion, String telCasa, String telMovil, 
                         double salarioBase, double bonificacion) {
        String sql = "INSERT INTO persona (dpi, primernombre, segundonombre, primerapellido, segundoapellido, "
                   + "direccion_domiciliar, telefono_casa, telefono_movil, salario_base, bonificacion) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dpi);
            stmt.setString(2, primerNombre);
            stmt.setString(3, segundoNombre);
            stmt.setString(4, primerApellido);
            stmt.setString(5, segundoApellido);
            stmt.setString(6, direccion);
            stmt.setString(7, telCasa);
            stmt.setString(8, telMovil);
            stmt.setDouble(9, salarioBase);
            stmt.setDouble(10, bonificacion);
            stmt.executeUpdate();
            System.out.println("Datos insertados en MySQL.");

            // Registrar en la bitácora
            AuditLog.registrarTransaccion("Insertar", "MySQL", "DPI: " + dpi);
        } catch (SQLException e) {
            System.out.println("Error al insertar en MySQL: " + e.getMessage());
        }
    }

    // Método para actualizar una persona en la base de datos MySQL
    public void actualizar(String dpi, String primerNombre, String segundoNombre, String primerApellido, 
                           String segundoApellido, String direccion, String telCasa, String telMovil, 
                           double salarioBase, double bonificacion) {
        String sql = "UPDATE persona SET primernombre = ?, segundonombre = ?, primerapellido = ?, segundoapellido = ?, "
                   + "direccion_domiciliar = ?, telefono_casa = ?, telefono_movil = ?, salario_base = ?, bonificacion = ? "
                   + "WHERE dpi = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, primerNombre);
            stmt.setString(2, segundoNombre);
            stmt.setString(3, primerApellido);
            stmt.setString(4, segundoApellido);
            stmt.setString(5, direccion);
            stmt.setString(6, telCasa);
            stmt.setString(7, telMovil);
            stmt.setDouble(8, salarioBase);
            stmt.setDouble(9, bonificacion);
            stmt.setString(10, dpi);
            stmt.executeUpdate();
            System.out.println("Datos actualizados en MySQL.");

            // Registrar en la bitácora
            AuditLog.registrarTransaccion("Actualizar", "MySQL", "DPI: " + dpi);
        } catch (SQLException e) {
            System.out.println("Error al actualizar en MySQL: " + e.getMessage());
        }
    }

    // Método para eliminar una persona en la base de datos MySQL
    public void eliminar(String dpi) {
        String sql = "DELETE FROM persona WHERE dpi = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dpi);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Datos eliminados en MySQL.");
                // Registrar en la bitácora
                AuditLog.registrarTransaccion("Eliminar", "MySQL", "DPI: " + dpi);
            } else {
                System.out.println("No se encontró el registro a eliminar en MySQL.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar en MySQL: " + e.getMessage());
        }
    }

    // Método para buscar una persona en la base de datos MySQL
    public Persona buscar(String dpi) {
        String sql = "SELECT * FROM persona WHERE dpi = ?";
        Persona persona = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dpi);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Recuperar datos del ResultSet y crear un objeto Persona
                persona = new Persona(
                    rs.getString("dpi"),
                    rs.getString("primernombre"),
                    rs.getString("segundonombre"),
                    rs.getString("primerapellido"),
                    rs.getString("segundoapellido"),
                    rs.getString("direccion_domiciliar"),
                    rs.getString("telefono_casa"),
                    rs.getString("telefono_movil"),
                    rs.getDouble("salario_base"),
                    rs.getDouble("bonificacion")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar en MySQL: " + e.getMessage());
        }

        return persona; // Retorna null si no se encontró
    }

    // Método para sincronizar datos con PostgreSQL
   public void sincronizarConPostgreSQL(PostgreSQLDatabase postgresDB) {
    String sql = "SELECT * FROM persona";

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            String dpi = rs.getString("dpi");
            // Buscar si ya existe en PostgreSQL
            if (postgresDB.buscar(dpi) == null) {
                // Si no existe, crear un objeto Persona
                Persona persona = new Persona(
                    dpi,
                    rs.getString("primernombre"),
                    rs.getString("segundonombre"),
                    rs.getString("primerapellido"),
                    rs.getString("segundoapellido"),
                    rs.getString("direccion_domiciliar"),
                    rs.getString("telefono_casa"),
                    rs.getString("telefono_movil"),
                    rs.getDouble("salario_base"),
                    rs.getDouble("bonificacion")
                );

                // Insertar en PostgreSQL
                postgresDB.insertar(persona);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al sincronizar desde MySQL a PostgreSQL: " + e.getMessage());
    }

    // Sincronizar desde PostgreSQL a MySQL
    postgresDB.sincronizarConMySQL(this);
}


    // Método para verificar si un DPI ya existe en la base de datos
    public boolean existe(String dpi) {
        String sql = "SELECT COUNT(*) FROM persona WHERE dpi = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dpi);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si existe, false si no
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar existencia en MySQL: " + e.getMessage());
        }

        return false;
    }

    // Método para cerrar la conexión (si se necesita)
    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión a MySQL cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión a MySQL: " + e.getMessage());
        }
    }
}
