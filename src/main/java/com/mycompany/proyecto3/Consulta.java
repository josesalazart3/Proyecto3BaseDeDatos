package com.mycompany.proyecto3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class Consulta {

    // Método para cargar datos desde la conexión proporcionada (MySQL o PostgreSQL)
    public DefaultTableModel cargarDatos(Connection connection) {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[] {
                "DPI", "Primer Nombre", "Segundo Nombre", "Primer Apellido", 
                "Segundo Apellido", "Dirección Domiciliar", 
                "Teléfono Casa", "Teléfono Móvil", 
                "Salario Base", "Bonificación"
            }, 0 // Número de filas inicialmente
        );
        
        try {
            // Crear el statement y ejecutar la consulta
            Statement stmt = connection.createStatement();
            String query = "SELECT dpi, primernombre, segundonombre, primerapellido, segundoapellido, "
                         + "direccion_domiciliar, telefono_casa, telefono_movil, salario_base, bonificacion "
                         + "FROM persona";
            ResultSet rs = stmt.executeQuery(query);
            
            // Recorrer el resultado y agregar filas al modelo de la tabla
            while (rs.next()) {
                modelo.addRow(new Object[] {
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
                });
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return modelo;
    }

    // Método para cargar datos desde la conexión proporcionada y tabla especificada
    public DefaultTableModel cargarDatos(Connection connection, String nombreTabla) {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[] {
                "DPI", "Primer Nombre", "Segundo Nombre", "Primer Apellido", 
                "Segundo Apellido", "Dirección Domiciliar", 
                "Teléfono Casa", "Teléfono Móvil", 
                "Salario Base", "Bonificación"
            }, 0 // Número de filas inicialmente
        );
        
        try {
            // Crear el statement y ejecutar la consulta
            Statement stmt = connection.createStatement();
            String query = "SELECT dpi, primernombre, segundonombre, primerapellido, segundoapellido, "
                         + "direccion_domiciliar, telefono_casa, telefono_movil, salario_base, bonificacion "
                         + "FROM " + nombreTabla;
            ResultSet rs = stmt.executeQuery(query);
            
            // Recorrer el resultado y agregar filas al modelo de la tabla
            while (rs.next()) {
                modelo.addRow(new Object[] {
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
                });
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return modelo;
    }
    
}
