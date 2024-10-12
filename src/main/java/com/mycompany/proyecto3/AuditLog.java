/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLog {

    private static final String LOG_FILE = "auditlog.txt";

    public static void registrarTransaccion(String operacion, String baseDatos, String detalle) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            LocalDateTime fechaHora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = fechaHora.format(formatter);

            writer.write(timestamp + " - " + operacion + " en " + baseDatos + ": " + detalle);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en la bitácora: " + e.getMessage());
        }
    }
}

