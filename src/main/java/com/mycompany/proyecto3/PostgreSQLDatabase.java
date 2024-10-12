package com.mycompany.proyecto3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLDatabase {

    private final Connection connection;

    public PostgreSQLDatabase(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar una persona en la base de datos PostgreSQL
    public void insertar(Persona persona) {
        String sql = "INSERT INTO persona (dpi, primernombre, segundonombre, primerapellido, segundoapellido, "
                   + "direccion_domiciliar, telefono_casa, telefono_movil, salario_base, bonificacion) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, persona.getDpi());
            stmt.setString(2, persona.getPrimerNombre());
            stmt.setString(3, persona.getSegundoNombre());
            stmt.setString(4, persona.getPrimerApellido());
            stmt.setString(5, persona.getSegundoApellido());
            stmt.setString(6, persona.getDireccion());
            stmt.setString(7, persona.getTelCasa());
            stmt.setString(8, persona.getTelMovil());
            stmt.setDouble(9, persona.getSalarioBase());
            stmt.setDouble(10, persona.getBonificacion());
            stmt.executeUpdate();
            System.out.println("Datos insertados en PostgreSQL.");

            // Registrar en la bitácora
            AuditLog.registrarTransaccion("Insertar", "PostgreSQL", "DPI: " + persona.getDpi());
        } catch (SQLException e) {
            System.out.println("Error al insertar en PostgreSQL: " + e.getMessage());
        }
    }

    // Método para actualizar una persona en la base de datos PostgreSQL
    public void actualizar(Persona persona) {
        String sql = "UPDATE persona SET primernombre = ?, segundonombre = ?, primerapellido = ?, segundoapellido = ?, "
                   + "direccion_domiciliar = ?, telefono_casa = ?, telefono_movil = ?, salario_base = ?, bonificacion = ? "
                   + "WHERE dpi = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, persona.getPrimerNombre());
            stmt.setString(2, persona.getSegundoNombre());
            stmt.setString(3, persona.getPrimerApellido());
            stmt.setString(4, persona.getSegundoApellido());
            stmt.setString(5, persona.getDireccion());
            stmt.setString(6, persona.getTelCasa());
            stmt.setString(7, persona.getTelMovil());
            stmt.setDouble(8, persona.getSalarioBase());
            stmt.setDouble(9, persona.getBonificacion());
            stmt.setString(10, persona.getDpi());
            stmt.executeUpdate();
            System.out.println("Datos actualizados en PostgreSQL.");

            // Registrar en la bitácora
            AuditLog.registrarTransaccion("Actualizar", "PostgreSQL", "DPI: " + persona.getDpi());
        } catch (SQLException e) {
            System.out.println("Error al actualizar en PostgreSQL: " + e.getMessage());
        }
    }

    // Método para eliminar una persona en la base de datos PostgreSQL
    public void eliminar(String dpi) {
        String sql = "DELETE FROM persona WHERE dpi = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dpi);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Datos eliminados en PostgreSQL.");
                // Registrar en la bitácora
                AuditLog.registrarTransaccion("Eliminar", "PostgreSQL", "DPI: " + dpi);
            } else {
                System.out.println("No se encontró el registro a eliminar en PostgreSQL.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar en PostgreSQL: " + e.getMessage());
        }
    }

    // Método para buscar una persona en la base de datos PostgreSQL
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
            System.out.println("Error al buscar en PostgreSQL: " + e.getMessage());
        }

        return persona; // Retorna null si no se encontró
    }

    // Método para sincronizar datos con MySQL
    public void sincronizarConMySQL(MySQLDatabase mysqlDB) {
        List<Persona> personas = obtenerTodosLosDatos();
        for (Persona persona : personas) {
            // Sincronizar cada persona
            mysqlDB.sincronizarConPostgreSQL(this);
        }
    }

    // Método para obtener todos los datos de la tabla
    public List<Persona> obtenerTodosLosDatos() {
        List<Persona> listaDatos = new ArrayList<>();

        String query = "SELECT dpi, primernombre, segundonombre, primerapellido, segundoapellido, " +
                       "direccion_domiciliar, telefono_casa, telefono_movil, salario_base, bonificacion FROM persona";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Persona persona = new Persona();
                persona.setDpi(rs.getString("dpi"));
                persona.setPrimerNombre(rs.getString("primernombre"));
                persona.setSegundoNombre(rs.getString("segundonombre"));
                persona.setPrimerApellido(rs.getString("primerapellido"));
                persona.setSegundoApellido(rs.getString("segundoapellido"));
                persona.setDireccion(rs.getString("direccion_domiciliar"));
                persona.setTelCasa(rs.getString("telefono_casa"));
                persona.setTelMovil(rs.getString("telefono_movil"));
                persona.setSalarioBase(rs.getDouble("salario_base"));
                persona.setBonificacion(rs.getDouble("bonificacion"));

                listaDatos.add(persona);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener datos de PostgreSQL: " + e.getMessage());
        }

        return listaDatos;
    }
}
