package com.mycompany.proyecto3;

import java.util.Scanner;

public class Proyecto3 {

    private static final int OPCION_MYSQL = 1;
    private static final int OPCION_POSTGRES = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Conexion conexion = new Conexion();
        MySQLDatabase mysqlDB = new MySQLDatabase(conexion.getMySQLConnection());
        PostgreSQLDatabase postgresDB = new PostgreSQLDatabase(conexion.getPostgresConnection());

        System.out.println("Seleccione la base de datos con la que desea trabajar:");
        System.out.println("1. MySQL");
        System.out.println("2. PostgreSQL");

        int seleccion = scanner.nextInt();
        scanner.nextLine();  // Consumir nueva línea
        boolean continuar = true;

        while (continuar) {
            System.out.println("Seleccione la operación que desea realizar:");
            System.out.println("1. Insertar");
            System.out.println("2. Actualizar");
            System.out.println("3. Eliminar");
            int operacion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

        }}

    private static void insertarDatosMySQL(String[] datosPersona, MySQLDatabase mysqlDB) {
        mysqlDB.insertar(datosPersona[0], datosPersona[1], datosPersona[2], datosPersona[3], datosPersona[4],
                         datosPersona[5], datosPersona[6], datosPersona[7],
                         Double.parseDouble(datosPersona[8]), Double.parseDouble(datosPersona[9]));
    }


    private static void eliminarDatosMySQL(String dpi, MySQLDatabase mysqlDB) {
        mysqlDB.eliminar(dpi);
    }

    private static void eliminarDatosPostgres(String dpi, PostgreSQLDatabase postgresDB) {
        postgresDB.eliminar(dpi);
    }

    private static String[] obtenerDatosPersona(Scanner scanner) {
        System.out.print("Ingrese DPI: ");
        String dpi = scanner.nextLine();
        System.out.print("Ingrese Primer Nombre: ");
        String primerNombre = scanner.nextLine();
        System.out.print("Ingrese Segundo Nombre: ");
        String segundoNombre = scanner.nextLine();
        System.out.print("Ingrese Primer Apellido: ");
        String primerApellido = scanner.nextLine();
        System.out.print("Ingrese Segundo Apellido: ");
        String segundoApellido = scanner.nextLine();
        System.out.print("Ingrese Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese Teléfono Casa: ");
        String telCasa = scanner.nextLine();
        System.out.print("Ingrese Teléfono Móvil: ");
        String telMovil = scanner.nextLine();
        System.out.print("Ingrese Salario Base: ");
        double salarioBase = scanner.nextDouble();
        System.out.print("Ingrese Bonificación: ");
        double bonificacion = scanner.nextDouble();
        scanner.nextLine(); // Consumir nueva línea

        return new String[]{dpi, primerNombre, segundoNombre, primerApellido, segundoApellido, direccion, telCasa, telMovil,
                            String.valueOf(salarioBase), String.valueOf(bonificacion)};
    }
}
