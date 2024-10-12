package com.mycompany.proyecto3;

public class Persona {

    // Atributos de la clase Persona
    private String dpi;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String direccion;
    private String telCasa;
    private String telMovil;
    private double salarioBase;
    private double bonificacion;

    // Constructor sin parámetros
    public Persona() {
    }

    // Constructor con parámetros
    public Persona(String dpi, String primerNombre, String segundoNombre, String primerApellido,
                   String segundoApellido, String direccion, String telCasa, String telMovil,
                   double salarioBase, double bonificacion) {
        this.dpi = dpi;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.direccion = direccion;
        this.telCasa = telCasa;
        this.telMovil = telMovil;
        this.salarioBase = salarioBase;
        this.bonificacion = bonificacion;
    }

    // Getters y Setters
    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelCasa() {
        return telCasa;
    }

    public void setTelCasa(String telCasa) {
        this.telCasa = telCasa;
    }

    public String getTelMovil() {
        return telMovil;
    }

    public void setTelMovil(String telMovil) {
        this.telMovil = telMovil;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        this.bonificacion = bonificacion;
    }
}
